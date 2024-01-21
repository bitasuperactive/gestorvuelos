package com.mycompany.gestorvuelos.dto;

import java.util.Date;

/**
 * Almacena datos más detallados sobre el vuelo que despega o recibe el
 * aeropuerto.
 *
 * @author PVita
 */
public class VueloDiario
{

    /**
     * Crea un objeto del vuelo diario.
     *
     * @param vueloBase Información base del vuelo que se va a efectuar.
     * @param fechaSalida Fecha de salida.
     * @param horaSalida Hora de salida.
     * @param horaLlegada Hora de llegada.
     * @param plazasOcupadas Plazas ocupadas.
     * @param precioPlaza Precio medio por plazas.
     */
    public VueloDiario(VueloBase vueloBase, Date fechaSalida, Date horaSalida, Date horaLlegada, int plazasOcupadas, float precioPlaza)
    {
        this.vueloBase = vueloBase;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.plazasOcupadas = plazasOcupadas;
        this.precioPlaza = precioPlaza;
    }

    public VueloBase getVueloBase()
    {
        return vueloBase;
    }

    public void setVueloBase(VueloBase vueloBase)
    {
        this.vueloBase = vueloBase;
    }

    public Date getFechaSalida()
    {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida)
    {
        this.fechaSalida = fechaSalida;
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

    public int getPlazasOcupadas()
    {
        return plazasOcupadas;
    }

    public void setPlazasOcupadas(int plazasOcupadas)
    {
        this.plazasOcupadas = plazasOcupadas;
    }

    public float getPrecioPlaza()
    {
        return precioPlaza;
    }

    public void setPrecioPlaza(float precioPlaza)
    {
        this.precioPlaza = precioPlaza;
    }

    private VueloBase vueloBase;
    // TODO - VALIDACIÓN: Hay que comprobar que es coherente con los días que opera el vuelo.
    private Date fechaSalida;
    // TODO - IMPLEMENTACIÓN: se registrarán las horas previstas pudiendo cambiarlas para reflejar si el vuelo llega o sale tarde.
    private Date horaSalida;
    // TODO - IMPLEMENTACIÓN: se registrarán las horas previstas pudiendo cambiarlas para reflejar si el vuelo llega o sale tarde.
    private Date horaLlegada;
    private int plazasOcupadas;
    private float precioPlaza;
}
