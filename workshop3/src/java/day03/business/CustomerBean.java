
package day03.business;

import day03.web.model.DiscountCode;
import day03.web.model.customer;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerBean 
{
    @PersistenceContext private EntityManager em;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Optional<customer> findByCustomerId(Integer custId)
    {
        customer c = em.find(customer.class, custId);
        return (Optional.ofNullable(c));
    }
    
    /**
     *
     * @param customer
     * @throws day03.business.CustomerException
     * @throws day03.business.CustomerException
     * @throws CustomerException
     */
    public void addNewCustomer(customer customer) throws CustomerException
    {
        DiscountCode code = em.find(DiscountCode.class, customer.getDiscountCode().getDiscountCode());
        
         if (null == code)
            throw new CustomerException("Discount code not found");
        
        //new
        customer.setDiscoutCode(code);
        
        em.persist(customer);
        //managed
                
    }
    
}
