
package day03.web.model;

import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


@Entity
@Table(name="purchase_order")
@NamedQueries({
    @NamedQuery(
        name="PurchaseOrder.findByCustomerId", 
        query="select pos from PurchaseOrder pos join pos.customer c where c.customerId = :custId"
        ),
        @NamedQuery(
                name = "PurchaseOrder.findByOrderNum",
                query = "select po from PurchaseOrder po where po.orderNum = :orderNum"
        )/*,
        @NamedQuery(
                name = "Interget.countPurchasedOrderByCustomer",
                query = "select c.customerId, count(po) from PurchaseOrder po join po.customer customer c where c.customerid"
        )*/
})
public class PurchaseOrder {
    
  @Id @Column(name="order_num")
   private Integer orderNum;
  
  @Column(name="product_id")
  private Integer productid;
  
  private Integer quantity;
  
  @Column(name="shipping_cost")
  private Float shippingcost;
  
  @Column(name="shipping_date")
  @Temporal(TemporalType.DATE)
  private Date shippingDate;
  
  @Column(name="sales_date")
  @Temporal(TemporalType.DATE)
  private Date salesDate;  
  
  @Column(name="freight_company")
  private String freightCompany;
  
  @ManyToOne
  @JoinColumn(name="customer_id", referencedColumnName="customer_id" )
  private customer customer;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getShippingcost() {
        return shippingcost;
    }

    public void setShippingcost(Float shippingcost) {
        this.shippingcost = shippingcost;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public String getFreightCompany() {
        return freightCompany;
    }

    public void setFreightCompany(String freightCompany) {
        this.freightCompany = freightCompany;
    }

    public customer getCustomer() {
        return customer;
    }

    public void setCustomer(customer customer) {
        this.customer = customer;
    }
  
  @Transient
  private Long gst;
  
  public JsonObject toJson()
  {
      JsonObjectBuilder builder = Json.createObjectBuilder();
      return builder.add("orderNum", orderNum)
              .add("productId", productid)
              .add("quantity", quantity)
              .add("shippingcost", shippingcost)
              .add("shippingDate", shippingDate.toString())
              .add("freightCompany", freightCompany)
              .build();
  }
}
