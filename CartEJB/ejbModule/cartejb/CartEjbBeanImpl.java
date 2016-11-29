package cartejb;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import entity.Good;
@Stateless  //ÎÞ×´Ì¬
//@Stateful //ÓÐ×´Ì¬
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
