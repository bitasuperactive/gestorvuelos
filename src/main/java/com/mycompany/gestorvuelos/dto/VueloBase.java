package com.mycompany.gestorvuelos.dto;

import java.util.Date;

/**
 * Almacena los datos básicos del vuelo que opera en el aeropuerto.
 */
public class VueloBase
{
    /*
    TODO - Los datos base serán completamente modificables mientras no haya 
    un vuelo concreto. Si ya ha salido o llegado un vuelo en una fecha concreta 
    no se podrá modificar ningún dato.
    */
    /**
     * Crea un vuelo base sin datos.
     */
    public VueloBase()
    {
        this.codigo = "";
        this.aeropuertoOrigen = new Aeropuerto();
        this.aeropuertoDestino = new Aeropuerto();
        this.plazas = 0;
        this.horaSalida = new Date();
        this.horaLlegada = new Date();
        this.diasOperacion = "";
    }

    /**
     * Crea un vuelo base.
     *
     * @param codigo Código identificador del vuelo.
     * @param aeropuertoOrigen Aeropuerto de partida.
     * @param aeropuertoDestino Aeropuerto de destino.
     * @param plazas Número de plazas disponible.
     * @param horaSalida Hora de salida.
     * @param horaLlegada Hora de llegada al destino.
     * @param diasOperacion Días de la semana en que opera el vuelo.
     */
    public VueloBase(String codigo, Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino, int plazas, Date horaSalida, Date horaLlegada, String diasOperacion)
    {
        this.codigo = codigo;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.plazas = plazas;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.diasOperacion = diasOperacion;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public Aeropuerto getAeropuertoOrigen()
    {
        return aeropuertoOrigen;
    }

    public void setAeropuertoOrigen(Aeropuerto aeropuertoOrigen)
    {
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public Aeropuerto getAeropuertoDestino()
    {
        return aeropuertoDestino;
    }

    public void setAeropuertoDestino(Aeropuerto aeropuertoDestino)
    {
        this.aeropuertoDestino = aeropuertoDestino;
    }

    public int getPlazas()
    {
        return plazas;
    }

    public void setPlazas(int plazas)
    {
        this.plazas = plazas;
    }

    public Date getHoraSalida()
    {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida)
    {
        this.horaSalida = horaSalida;
    }

    public Date getHoraLlegada()
    {
        return horaLlegada;
    }

    public void setHoraLlegada(Date horaLlegada)
    {
        this.horaLlegada = horaLlegada;
    }

    public String getDiasOperacion()
    {
        return diasOperacion;
    }

    public void setDiasOperacion(String diasOperacion)
    {
        this.diasOperacion = diasOperacion;
    }

    // TODO - FORMATO: Se compone del código de la compañía y un número entero 
    // positivo cuyo máximo es 9999.
    // Ejemplos de códigos de vuelos válidos: V73585, IB480
    private String codigo;
    private Aeropuerto aeropuertoOrigen;
    private Aeropuerto aeropuertoDestino;
    private int plazas;
    private Date horaSalida;
    private Date horaLlegada;
    // TODO - FORMATO: Cadena con siete caracteres que contiene qué días de la semana opera el vuelo. Por ejemplo, si lo hace todos los días: “LMXJVSD”.
    private String diasOperacion;
}
