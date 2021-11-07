package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import java.util.Date;
import java.util.Objects;

public class ExchangeRateDTO {

    private Casa casa;

    private Date d;
    private Double v;
    private String nombre;

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public Double getV() {
        return Double.parseDouble(this.getCasa().getVenta());
    }

    public void setV(Double v) {
        this.v = v;
    }

    public String getNombre() {
        return this.getCasa().getNombre();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public boolean isOfficialRate() {
        return Objects.equals(this.getCasa().getNombre(), "Dolar Oficial");
    }
}

class Casa{
    private String venta;
    private String nombre;

    public String getVenta() {
        return venta.replace(",", ".");
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}