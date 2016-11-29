package cartejb;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import entity.Good;

@Stateless  //ÎÞ×´Ì¬
//@Stateful //ÓÐ×´Ì¬
@Remote(GoodRemote.class)
public class GoodBeanImpl implements GoodRemote {

	public String printGoodInfo(Good good) {
		// TODO Auto-generated method stub
		return good.toString();
	}

}
