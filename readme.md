搞了一晚上加一白天，最后下了个jboss5终于好了。。。每次都是jboss配置问题。。。

----------
开发部署一个会话bean，包括有状态和无状态，模拟一个简单购物车

首先服务端
-----
无状态和有状态注解是
//@Stateless  //无状态
//@Stateful //有状态
将如下类中注解改一下即可改为有状态/无状态

----------

CartEjb 接口：
```
package cartejb;

public interface CartEjb {
	public String print();

	public void jishuqi(int i);

	public int getResult();
}

```
对应的实现类：

```
package cartejb;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entity.Good;

@Stateless  //无状态
//@Stateful //有状态
@Remote(CartEjb.class)
public class CartEjbBeanImpl implements CartEjb {
	@EJB(beanName = "GoodRemoteImpl")
	GoodRemote goodBean;
	private int state;

	public void jishuqi(int i) {
		state += i;
	}

	public int getResult() {
		return state;
	}

	Good good = new Good(1001, "Milk", 10, 5);

	public String print() {
		return goodBean.printGoodInfo(good);
	}

}

```
Good商品信息相关接口：
```
package cartejb;

import entity.Good;

public interface GoodRemote {
	public String printGoodInfo(Good good);

}

```
对应实现类：

```
package cartejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import entity.Good;

@Stateless  //无状态
//@Stateful //有状态
@Remote(GoodRemote.class)
public class GoodBeanImpl implements GoodRemote {

	public String printGoodInfo(Good good) {
		// TODO Auto-generated method stub
		return good.toString();
	}

}

```

然后是实体类：
```
package entity;

import java.io.Serializable;

public class Good implements Serializable{
	private int id;
	private String name;
	private float price;
	private int amount;
	
	public Good(int id, String name, float price, int amount) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "购物车内商品：[id=" + id + ", name=" + name + ", price=" + price
				+ ", amount=" + amount + "]";
	}

}

```

客户端：
----

```
package client;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import cartejb.CartEjb;

public class CartEjbClient {

	public static void main(String[] args) throws NamingException {
		// TODO Auto-generated method stub
		/*
		 * Properties props = new Properties();
		 * 
		 * props.setProperty("java.naming.factory.initial",
		 * "org.jnp.interfaces.NamingContextFactory");
		 * props.setProperty("java.naming.factory.url.pkgs"
		 * ,"org.jboss.naming:org.jnp.interfaces");
		 * props.setProperty("java.naming.provider.url","jnp://localhost:1099");
		 */
		// InitialContext ctx = new InitialContext(props);
		InitialContext ctx = new InitialContext();
		CartEjb cartejb = (CartEjb) ctx.lookup("CartEjbBeanImpl/remote");
		/*
		 * Good good=new Good(); good.setId("1011"); good.setName("Milk");
		 * good.setAmount(5); good.setPrice(10);
		 */
			
		System.out.println("---------第一次会话-----------");
	
		System.out.println("第" + cartejb.getResult() + "个顾客：" + "\n");
		String s = cartejb.print();
		System.out.println(s);
		cartejb.jishuqi(1);
		System.out.println("第" + cartejb.getResult() + "个顾客：" + "\n");
		String s2 = cartejb.print();
		System.out.println(s2);
		cartejb.jishuqi(1);
		System.out.println("第" + cartejb.getResult() + "个顾客：" + "\n");
		String s3 = cartejb.print();
		System.out.println(s3);

		System.out.println("---------第二次会话-----------");

		CartEjb cartejb2 = (CartEjb) ctx.lookup("CartEjbBeanImpl/remote");
		
		System.out.println("第" + cartejb.getResult() + "个顾客：" + "\n");
		
		System.out.println(cartejb2.print());
		cartejb2.jishuqi(1);
		System.out.println("第" + cartejb.getResult() + "个顾客：" + "\n");
		
		System.out.println(cartejb2.print());
		cartejb2.jishuqi(1);
		System.out.println("第" + cartejb.getResult() + "个顾客：" + "\n");
		System.out.println(cartejb2.print());

	}

}

```

----------
然后就是jndi.properties

```
java.naming.factory.initial=org.jnp.interfaces.NamingContextFactory
java.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces
java.naming.provider.url=localhost
```
把服务端export出jar然后客户端导入，或者直接add projects 服务端项目；再把jboss下client里的jbossall-client.jar导入客户端类路径。


----------

运行：
---
**无状态会话bean结果**
![无状态会话bean结果](http://img.blog.csdn.net/20161129133852849)
**有状态会话bean结果**
![有状态会话bean结果](http://img.blog.csdn.net/20161129133406763)


结论
----

有状态的会话Bean,每一个会话 都是一个**新的开始**。而无状态的会话Bean 每个会话用的时候都会**接着用上一次的会话**。 
       有状态会话bean ：**每个用户有自己特有的一个实例**，在用户的生存期内，bean保持了用户的信息，即“有状态”；一旦用户灭亡（调用结束或实例结束），bean的生命期也告结束。即每个用户最初都会得到一个初始的bean。 
       无状态会话bean ：bean一旦实例化就被加进会话池中，**各个用户都可以共用**。即使用户已经消亡，bean 的生命期也不一定结束，它可能依然存在于会话池中，供其他用户调用。由于没有特定的用户，那么也就不能保持某一用户的状态，所以叫无状态bean。但无状态会话bean 并非没有状态，如果它有自己的属性（变量），那么这些变量就会受到所有调用它的用户的影响，这是在实际应用中必须注意的。