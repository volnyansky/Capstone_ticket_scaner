
package ua.dp.vedanta.ticketchecker.api;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TicketJson {

    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("barcode_token")
    @Expose
    private String barcodeToken;
    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("used")
    @Expose
    private Boolean used;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("changed")
    @Expose
    private long changed;
    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("line_item")
    @Expose
    private LineItem lineItem;
    @SerializedName("product")
    @Expose
    private Product product;

    /**
     * 
     * @return
     *     The valid
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 
     * @param valid
     *     The valid
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * 
     * @return
     *     The reason
     */
    public Object getReason() {
        return reason;
    }

    /**
     * 
     * @param reason
     *     The reason
     */
    public void setReason(Object reason) {
        this.reason = reason;
    }

    /**
     * 
     * @return
     *     The barcodeToken
     */
    public String getBarcodeToken() {
        return barcodeToken;
    }

    /**
     * 
     * @param barcodeToken
     *     The barcode_token
     */
    public void setBarcodeToken(String barcodeToken) {
        this.barcodeToken = barcodeToken;
    }

    /**
     * 
     * @return
     *     The ticketId
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * 
     * @param ticketId
     *     The ticket_id
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * 
     * @return
     *     The used
     */
    public Boolean getUsed() {
        return used;
    }

    /**
     * 
     * @param used
     *     The used
     */
    public void setUsed(Boolean used) {
        this.used = used;
    }

    /**
     * 
     * @return
     *     The position
     */
    public String getPosition() {
        return position;
    }

    /**
     * 
     * @param position
     *     The position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * 
     * @return
     *     The changed
     */
    public long getChanged() {
        return changed;
    }

    /**
     * 
     * @param changed
     *     The changed
     */
    public void setChanged(long changed) {
        this.changed = changed;
    }

    /**
     * 
     * @return
     *     The order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * 
     * @param order
     *     The order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * 
     * @return
     *     The lineItem
     */
    public LineItem getLineItem() {
        return lineItem;
    }

    /**
     * 
     * @param lineItem
     *     The line_item
     */
    public void setLineItem(LineItem lineItem) {
        this.lineItem = lineItem;
    }

    /**
     * 
     * @return
     *     The product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * 
     * @param product
     *     The product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

}
