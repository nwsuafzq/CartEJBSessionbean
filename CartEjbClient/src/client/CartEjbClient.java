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
		//String s = cartejb.print();
		System.out.println(cartejb.print());
		cartejb.jishuqi(1);
		System.out.println("第" + cartejb.getResult() + "个顾客：" + "\n");
		//String s2 = cartejb.print();
		System.out.println(cartejb.print());
		cartejb.jishuqi(1);
		System.out.println("第" + cartejb.getResult() + "个顾客：" + "\n");
		//String s3 = cartejb.print();
		System.out.println(cartejb.print());

		System.out.println("---------第二次会话-----------");

		CartEjb cartejb2 = (CartEjb) ctx.lookup("CartEjbBeanImpl/remote");
		
		System.out.println("第" + cartejb2.getResult() + "个顾客：" + "\n");
		
		System.out.println(cartejb2.print());
		cartejb2.jishuqi(1);
		System.out.println("第" + cartejb2.getResult() + "个顾客：" + "\n");
		
		System.out.println(cartejb2.print());
		cartejb2.jishuqi(1);
		System.out.println("第" + cartejb2.getResult() + "个顾客：" + "\n");
		System.out.println(cartejb2.print());

	}

}
