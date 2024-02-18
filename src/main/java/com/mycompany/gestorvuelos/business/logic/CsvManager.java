package com.mycompany.gestorvuelos.business.logic;

import com.mycompany.gestorvuelos.dto.Aeropuerto;
import com.mycompany.gestorvuelos.dto.Compania;
import com.mycompany.gestorvuelos.dto.VueloBase;
import com.mycompany.gestorvuelos.dto.VueloDiario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Compendio de funciones relativas al guardado, recuperación y actualización de archivos CSV.
 */
public class CsvManager
{
    /**
     * Crea la lista de aeropuertos disponibles.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del CSV.</pre>
     * @throws java.io.IOException Si el archivo CSV es inaccesible (no debería 
     * producirse tras las validaciones realizadas en PropertiesManager).
     * @return Lista de aeropuertos.
     * @see Aeropuerto
     */
    protected static List<Aeropuerto> retrieveListAeropuerto() throws IOException
    {
        ArrayList<Aeropuerto> listAeropuerto = new ArrayList();
        
        File aeropuertosCsvFile = Util.getCsvFiles().getAeropuertos();
        
        // Codificación UTF-8 con BOM.
        BufferedReader reader = new BufferedReader(new FileReader(aeropuertosCsvFile, StandardCharsets.UTF_8));
        
        // Omitir encabezados de las columnas.
        reader.readLine();
        
        String row;
        while ((row = reader.readLine()) != null) {
            Aeropuerto aeropuerto = new Aeropuerto();
            String[] aeropuertoData = Arrays.stream(row.split(";"))
                    .map(str -> str.trim())
                    .toArray(String[]::new);

            aeropuerto.setCodigoIATA(aeropuertoData[0]);
            aeropuerto.setNombre(aeropuertoData[1]);
            
            String municipio = aeropuertoData[2];
            int codigoMunicipio = Util.getMapMunicipios().getOrDefault(municipio, 0);
            aeropuerto.setCodigoMunicipio(codigoMunicipio);
            
            listAeropuerto.add(aeropuerto);
        }

        return listAeropuerto;
    }
    
    /**
     * Crea la lista de compañias disponibles.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del CSV.</pre>
     * @return Lista de compañias.
     * @throws java.io.IOException Si el archivo CSV es inaccesible (no debería 
     * producirse tras las validaciones realizadas en PropertiesManager).
     * @throws ArrayIndexOutOfBoundsException Si los campos opcionales estan parcialmente rellenados.
     * @see Compania
     */
    protected static List<Compania> retrieveListCompania() throws IOException, ArrayIndexOutOfBoundsException
    {
        ArrayList<Compania> listCompania = new ArrayList();
        
        File companiasCsvFile = Util.getCsvFiles().getCompanias();
        
        // Codificación UTF-8 con BOM.
        BufferedReader reader = new BufferedReader(new FileReader(companiasCsvFile, StandardCharsets.UTF_8));
        
        // Omitir encabezados de las columnas.
        reader.readLine();
        
        String row;
        while ((row = reader.readLine()) != null) {
            Compania compania = new Compania();
            String[] companiaData = Arrays.stream(row.split(";"))
                    .map(str -> str.trim())
                    .toArray(String[]::new);

            // Los campos prefijo, código y nombre son obligatorios,
            // asumimos que estarán presentes.
            compania.setPrefijo(Short.parseShort(companiaData[0]));
            compania.setCodigo(companiaData[1]);
            compania.setNombre(companiaData[2]);
            
            // Los siguientes campos son opcionales pero deben estar o todos vacíos
            // o todos rellenados.
            if (companiaData.length > 3) // TODO - Mejorar la recuperación de los datos de las compaías.
            {
                if (companiaData.length < 7)
                    throw new ArrayIndexOutOfBoundsException("Los valores opcionales estan incompletos.");
                
                compania.setDireccionSedeCentral(companiaData[3]);
                compania.setMunicipioSedeCentral(companiaData[4]);
                compania.setTelefonoATA(companiaData[5]);
                compania.setTelefonoATC(companiaData[6]);
            }
            
            listCompania.add(compania);
        }

        return listCompania;
    }
    
    /**
     * Crea la lista de vuelos base disponibles.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del CSV.</pre>
     * @return Lista de vuelos base disponibles.
     * @throws IOException Si el archivo CSV es inaccesible (no debería 
     * producirse tras las validaciones realizadas en PropertiesManager).
     * @see VueloBase
     * @see ListManager#getAeropuertoByCodigoIATA(java.lang.String)
     */
    public static List<VueloBase> retrieveListVueloBase() throws IOException
    {
        List<VueloBase> listVueloBase = new ArrayList();
        File vuelosBaseCsvFile = Util.getCsvFiles().getVuelosBase();
        
        // Codificación UTF-8 sin BOM.
        BufferedReader reader = new BufferedReader(new FileReader(vuelosBaseCsvFile, StandardCharsets.UTF_8));
        
        // Saltar encabezados.
        reader.readLine();
        
        String row;
        while ((row = reader.readLine()) != null) {
            VueloBase vueloBase = new VueloBase();
            String[] vueloBaseData = Arrays.stream(row.split(";"))
                    .map(str -> str.trim())
                    .toArray(String[]::new);
            
            vueloBase.setCodigo(vueloBaseData[0]);
            
            String codigoAeropuertoOrigen = vueloBaseData[1];
            Aeropuerto aeropuertoOrigen;
            try {
                aeropuertoOrigen = ListManager.getAeropuertoByCodigoIATA(codigoAeropuertoOrigen);
            } catch (NoSuchElementException ex) {
                aeropuertoOrigen = null;
            }
            vueloBase.setAeropuertoOrigen(aeropuertoOrigen);
            
            String codigoAeropuertoDestino = vueloBaseData[2];
            Aeropuerto aeropuertoDestino;
            try {
                aeropuertoDestino = ListManager.getAeropuertoByCodigoIATA(codigoAeropuertoDestino);
            } catch (NoSuchElementException ex) {
                aeropuertoDestino = null;
            }
            vueloBase.setAeropuertoDestino(aeropuertoDestino);
            
            int plazas = Integer.parseInt(vueloBaseData[3]);
            vueloBase.setPlazas(plazas);
            
            String horaSalidaString = vueloBaseData[4];
            Date horaSalida;
            try {
                horaSalida = DateUtils.parseHour(horaSalidaString);
            } catch (ParseException ex) {
                horaSalida = null;
            }
            vueloBase.setHoraSalida(horaSalida);
            
            String horaLlegadaString = vueloBaseData[5];
            Date horaLlegada;
            try {
                horaLlegada = DateUtils.parseHour(horaLlegadaString);
            } catch (ParseException ex) {
                horaLlegada = null;
            }
            vueloBase.setHoraLlegada(horaLlegada);
            
            vueloBase.setDiasOperacion(vueloBaseData[6]);
            
            listVueloBase.add(vueloBase);
        }
        
        return listVueloBase;
    }
    
    
    /**
     * Crea la lista de vuelos diarios disponibles.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del CSV.</pre>
     * @return Lista de vuelos diarios disponibles.
     * @throws IOException Si el archivo CSV es inaccesible (no debería 
     * producirse tras las validaciones realizadas en PropertiesManager).
     * @see VueloDiario
     * @see ListManager#getVueloBaseByCodigo(java.lang.String)
     */
    public static List<VueloDiario> retrieveListVueloDiario() throws IOException
    {
        List<VueloDiario> listVueloDiario = new ArrayList();
        File vuelosDiariosCsvFile = Util.getCsvFiles().getVuelosDiarios();
        
        // Codificación UTF-8 sin BOM.
        BufferedReader reader = new BufferedReader(new FileReader(vuelosDiariosCsvFile, StandardCharsets.UTF_8));
        
        // Saltar encabezados.
        reader.readLine();
        
        String row;
        while ((row = reader.readLine()) != null) {
            VueloDiario vueloDiario = new VueloDiario();
            String[] vueloDiarioData = Arrays.stream(row.split(";"))
                    .map(str -> str.trim())
                    .toArray(String[]::new);
            
            String codigoVueloBase = vueloDiarioData[0];
            VueloBase vueloBase;
            try {
                vueloBase = ListManager.getVueloBaseByCodigo(codigoVueloBase);
            } catch (NoSuchElementException ex) {
                vueloBase = null;
            }
            vueloDiario.setVueloBase(vueloBase);
            
            String fechaSalidaString = vueloDiarioData[1];
            Date fechaSalida;
            try {
                fechaSalida = DateUtils.getDate(fechaSalidaString);
            } catch (ParseException ex) {
                fechaSalida = null;
            }
            vueloDiario.setFechaSalida(fechaSalida);
        
            String horaSalidaString = vueloDiarioData[2];
            Date horaSalida;
            try {
                horaSalida = DateUtils.parseHour(horaSalidaString);
            } catch (ParseException ex) {
                horaSalida = null;
            }
            vueloDiario.setHoraSalida(horaSalida);
        
            String horaLlegadaString = vueloDiarioData[3];
            Date horaLlegada;
            try {
                horaLlegada = DateUtils.parseHour(horaLlegadaString);
            } catch (ParseException ex) {
                horaLlegada = null;
            }
            vueloDiario.setHoraLlegada(horaLlegada);
        
            int plazasOcupadas = Integer.parseInt(vueloDiarioData[4]);
            vueloDiario.setPlazasOcupadas(plazasOcupadas);
            
            float precioPlaza = Float.parseFloat(vueloDiarioData[5]);
            vueloDiario.setPrecioPlaza(precioPlaza);
            
            listVueloDiario.add(vueloDiario);
        }
        
        return listVueloDiario;
    }
    
    /**
     * Crea un mapa de municipios. 
     * <br> Clave: Nombre del municipio.
     * <br> Valor: Código identificativo del municipio.
     * <br><br>
     * <b>Limitaciones:</b> <br> 
     * <pre>     No se valida el contenido del CSV.</pre>
     * @return Map<Municipio, Código>
     * @throws java.io.IOException Si el archivo CSV es inaccesible (no debería 
     * producirse tras las validaciones realizadas en PropertiesManager).
     */
    protected static Map<String, Integer> retrieveMapMunicipios() throws IOException
    {
        Map<String, Integer> mapMunicipios = new HashMap();
        File municipiosCsvFile = Util.getCsvFiles().getMunicipios();
        
        // Codificación ANSI.
        BufferedReader reader = new BufferedReader(new FileReader(municipiosCsvFile, Charset.forName("Cp1252")));
        
        // Omitir encabezados de las columnas.
        reader.readLine();
        
        String row;
        while ((row = reader.readLine()) != null) {
            String[] municipioData = Arrays.stream(row.split(";"))
                    .map(str -> str.trim())
                    .toArray(String[]::new);
            
            int codigoMunicipio = Integer.parseInt(municipioData[0]);
            
            String municipio = municipioData[1];
            
            mapMunicipios.put(municipio, codigoMunicipio);
        }
        
        return mapMunicipios;
    }
}
