
package ua.dp.vedanta.ticketchecker.api;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class LineItem {

    @SerializedName("line_item_id")
    @Expose
    private String lineItemId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unit_price")
    @Expose
    private float unitPrice;
    @SerializedName("total_price")
    @Expose
    private float totalPrice;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("changed")
    @Expose
    private String changed;

    /**
     * 
     * @return
     *     The lineItemId
     */
    public String getLineItemId() {
        return lineItemId;
    }

    /**
     * 
     * @param lineItemId
     *     The line_item_id
     */
    public void setLineItemId(String lineItemId) {
        this.lineItemId = lineItemId;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return
     *     The unitPrice
     */
    public float getUnitPrice() {
        return unitPrice;
    }

    /**
     * 
     * @param unitPrice
     *     The unit_price
     */
    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 
     * @return
     *     The totalPrice
     */
    public float getTotalPrice() {
        return totalPrice;
    }

    /**
     * 
     * @param totalPrice
     *     The total_price
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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
    public String getChanged() {
        return changed;
    }

    /**
     * 
     * @param changed
     *     The changed
     */
    public void setChanged(String changed) {
        this.changed = changed;
    }

}
