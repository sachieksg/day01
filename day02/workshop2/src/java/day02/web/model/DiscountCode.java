/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day02.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Table( name="discount_code" )
public class DiscountCode
{
   public enum Code { H, L, M, N}
   
   @Id @Column(name="discount_code")
   @Enumerated(EnumType.STRING)
   private Code discountCode;
    
   private Float rate;
   
   @OneToMany(mappedBy = "discountCode")
   private List<customer> customers;
   
    public Code getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(Code discountCode) {
        this.discountCode = discountCode;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public List<customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<customer> customers) {
        this.customers = customers;
    }
            


    
}
