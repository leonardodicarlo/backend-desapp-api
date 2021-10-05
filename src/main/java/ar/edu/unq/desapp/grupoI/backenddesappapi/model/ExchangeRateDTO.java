package ar.edu.unq.desapp.grupoi.backenddesappapi.model;

import java.util.Date;

public class ExchangeRateDTO {

    private Date d;
    private Double v;

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }
}
