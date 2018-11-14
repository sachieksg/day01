
package day03.web.model;

import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class customer 
{
     @Id @Column(name="customer_id")
    private Integer customerId;
    
    private String zip;
    private String name;
    private String addressline1;
    private String addressline2;        
    private String city;   
    private String state; 
    private String phone; 
    private String fax;     
    private String email;
    
    @Column(name="credit_limit")
    private String creditlimit;  

   @ManyToOne
   @JoinColumn(name ="discount_code", referencedColumnName = "discount_code")
   private DiscountCode discountCode;
   
   @OneToMany(mappedBy = "customer")
   private List<PurchaseOrder> purchaseOrders;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(String creditlimit) {
        this.creditlimit = creditlimit;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
    
     public JsonObject toJson()
  {
      JsonObjectBuilder builder = Json.createObjectBuilder();
      return builder.add("customerId", customerId)
              .add("name", name)
              .add("addressline1", addressline1)
              .add("addressline2", addressline2)
              .add("city", city)
              .add("state", state)
              .add("zip", zip)
              .add("phone", phone)
              .add("fax", fax)
              .add("email", email)
              .add("creditlimit", creditlimit)
              .add("discountCode", discountCode.getDiscountCode().toString())
              .build();
  }

    public void setDiscoutCode(DiscountCode discoutCode) {
        this.discountCode = discoutCode;
    }
}
   