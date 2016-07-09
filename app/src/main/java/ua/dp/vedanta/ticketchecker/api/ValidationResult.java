package ua.dp.vedanta.ticketchecker.api;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ValidationResult {

    @SerializedName("validated")
    @Expose
    private Boolean validated;

    /**
     *
     * @return
     * The validated
     */
    public Boolean getValidated() {
        return validated;
    }

    /**
     *
     * @param validated
     * The validated
     */
    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

}