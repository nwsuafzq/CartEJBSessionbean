package cartejb;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import entity.Good;

@Stateless  //��״̬
//@Stateful //��״̬
@Remote(GoodRemote.class)
public class GoodBeanImpl implements GoodRemote {

	public String printGoodInfo(Good good) {
		// TODO Auto-generated method stub
		return good.toString();
	}

}
