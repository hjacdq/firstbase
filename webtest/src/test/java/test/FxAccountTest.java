package test;
//
//
//import net.zhunaer.dal.daointerface.FxAccountDAO;
//import net.zhunaer.dal.dataobject.FxAccount;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath*:applicationContext-common-test.xml")
//public class FxAccountTest {
//
//  @Autowired
//  private FxAccountDAO fxAccountDAO;
//  
//  
//  @Test
//  public void add(){
//    FxAccount a=new FxAccount();
//    a.setDescription("description");
//    a.setFxFrom(1);
//    a.setFxId(1l);
//    a.setPaid(1000d);
//    a.setStatus(1);
//    a.setTotalCommission(1000d);
//    a.setUnpaid(100d);
//    
//    Long id= fxAccountDAO.insert(a);
//    System.out.println(id);
//  }
//  
//  
//  
//  @Test
//  public void update(){
//    FxAccount a=fxAccountDAO.selectByPrimaryKey(FxAccount.class, 1l);
//    a.setDescription("description");
//    a.setFxFrom(1);
//    a.setFxId(1l);
//    a.setPaid(1000d);
//    a.setStatus(1);
//    a.setTotalCommission(1000d);
//    a.setUnpaid(100d);
//    
//    int r= fxAccountDAO.updateByPrimaryKey(a);
//    System.out.println(r);
//  }
//  
//  
//  
//  @Test
//  public void delete(){
//    int r= fxAccountDAO.deleteByPrimaryKey(5l);
//    System.out.println(r);
//  }
//  
//  
//  
//  
//  
//}
