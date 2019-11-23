import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventarioBot extends TelegramLongPollingBot {

    public String seccion = "";
    public String accion = "";
    public String campo = "";
    public String codigo = "";
    public String sucursal = "";
    public String nombre = "";
    public String direccion = "";
    public String celular = "";
    public String tipo = "";
    public String descripcion = "";
    public boolean msj_empleados = true;
    public boolean msj_productos = true;

    // FUNCION PARA OBTENER LOS NUEVOS MENSAJES ENVIADOS AL BOT
    public void onUpdateReceived(Update update) {
        // OBTENER EL MENSAJE QUE ENVIA EL USUARIO
        String texto = update.getMessage().getText();

        // INICIAR EL MENÚ
        menuInicial(texto, update);

        // OBTENER EL NOMBRE DEL USUARIO QUE ENVIO EL MENSAJE
       // String nombre_usuario = update.getMessage().getFrom().getFirstName();
    }

    public String getBotUsername() {
        return "nombreusu_bot";
    }

    public String getBotToken() {
        return "1054642092:AAGsEk1Lhiwg6se60Jhcr_E1GHQptb3104Y";
    }

    /****************************
    * SECCIÓN MENÚ DE MENSAJES
    * ***************************/

    // Menu inicial
    public void menuInicial(String comando,Update update)
    {
        // RESPONDER AL USUARIO
        SendMessage mensaje = new SendMessage();
        // ARMAR EL MENSAJE
        String texto_mensaje = "";
        // OPCIONES
        ReplyKeyboardMarkup kb = new ReplyKeyboardMarkup();
        //Crear una lista que guarde las opciones
        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();

        // RESPUESTA
        if(comando.equals("/almacen"))
        {
            seccion = "INICIO";
            keyboard.clear();
            row.clear();
            // AGREGAR LAS OPCIONES
            row.add("1. EMPLEADOS");//Opcion1
            // AGREGAR LOS DATOS A LA LISTA
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("2. PRODUCTOS");//Opcion2
            keyboard.add(row);

            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("1. EMPLEADOS") || comando.equals("/EMPLEADOS")) {
            seccion = "EMPLEADOS";
            keyboard.clear();
            row.clear();
            // AGREGAR LOS DATOS A LA LISTA
            row.add("1. REGISTRAR");//opcion1
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("2. MODIFICAR");//Opcion2
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("3. ELIMINAR");//Opcion3
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("4. MOSTRAR");//Opcion4
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("5. LISTAR");//Opcion5
            keyboard.add(row);

            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("2. PRODUCTOS") || comando.equals("/PRODUCTOS")){
            seccion = "PRODUCTOS";
            keyboard.clear();
            row.clear();
            // AGREGAR LOS DATOS A LA LISTA
            row.add("1. REGISTRAR");//opcion1
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("2. MODIFICAR");//Opcion2
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("3. ELIMINAR");//Opcion3
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("4. MOSTRAR");//Opcion4
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("5. LISTAR");//Opcion5
            keyboard.add(row);

            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("1. REGISTRAR") || accion.equals("REGISTRAR"))
        {
            keyboard.clear();
            row.clear();
            // AGREGAR LOS DATOS A LA LISTA
            if(seccion.equals("EMPLEADOS")) {
                if(accion.equals(""))
                {
                    texto_mensaje = "DATOS QUE SE NECESITAN:\n1. NOMBRE*\n2. DIRECCIÓN*\n3. CELULAR*\nDEBE IR ELIGIENDO LOS CAMPOS(LOS CAMPOS CON * SON OBLIGATORIOS)";
                }
                // ASIGNAR LA ACCION ACTUAL
                accion = "REGISTRAR";

                // ASIGNAR CAMPOS
                if(!comando.equals("1. NOMBRE") && !comando.equals("2. DIRECCIÓN") && !comando.equals("3. CELULAR") && !comando.equals("4. SUCURSAL")  && !comando.equals("GUARDAR")  && !comando.equals("CANCELAR"))
                {
                    if(campo.equals("nombre"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(direccion != "" && celular != "" && sucursal != "")
                        {
                            texto_mensaje = "CORRECTO!";
                        }

                        nombre = comando;
                    }
                    else if(campo.equals("direccion"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(nombre != "" && celular != "" && sucursal != "") {
                            texto_mensaje = "CORRECTO!";
                        }

                        direccion = comando;
                    }
                    else if(campo.equals("celular"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(nombre != "" && direccion != "" && sucursal != ""){
                            texto_mensaje = "CORRECTO!";
                        }

                        celular = comando;
                    }
                    else if(campo.equals("sucursal"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(nombre != "" && direccion != "" && celular != ""){
                            texto_mensaje = "CORRECTO!";
                        }

                        sucursal = comando;
                    }
                }
                //FIN ASIGANAR CAMPOS

                if(nombre == "")
                {
                    row.add("1. NOMBRE");//opcion1
                    keyboard.add(row);
                }
                if(direccion == "")
                {
                    row = new KeyboardRow();
                    row.add("2. DIRECCIÓN");//Opcion2
                    keyboard.add(row);
                }
                if(celular == "")
                {
                    row = new KeyboardRow();
                    row.add("3. CELULAR");//Opcion3
                    keyboard.add(row);
                }
                if(sucursal == "")
                {
                    row = new KeyboardRow();
                    row.add("4. SUCURSAL");//Opcion3
                    keyboard.add(row);
                }
                // PEDIR DATOS

                if(comando.equals("1. NOMBRE"))
                {
                    texto_mensaje = "INGRESE EL NOMBRE DEL EMPLEADO:";
                    campo = "nombre";
                }
                else if(comando.equals("2. DIRECCIÓN"))
                {
                    texto_mensaje = "INGRESE LA DIRECCIÓN DEL EMPLEADO:";
                    campo = "direccion";
                }
                else if(comando.equals("3. CELULAR"))
                {
                    texto_mensaje = "INGRESE EL CELULAR DEL EMPLEADO:";
                    campo = "celular";
                }
                else if(comando.equals("4. SUCURSAL"))
                {
                    texto_mensaje = "SELECCIONE LA SUCURSAL:";
                    campo = "sucursal";
                    ResultSet res = sucursales();
                    int contador = 0;
                    try{
                        if(res != null)
                        {
                            keyboard.clear();
                            row.clear();
                            while(res.next())
                            {
                                System.out.println(res.getString("nombre"));
                                row = new KeyboardRow();
                                row.add(res.getString("nombre"));//sucursal
                                keyboard.add(row);
                                contador++;
                            }
                        }
                        if(contador == 0)
                        {
                            seccion = "INICIO";
                            keyboard.clear();
                            row.clear();
                            // AGREGAR LAS OPCIONES
                            row.add("1. EMPLEADOS");//Opcion1
                            // AGREGAR LOS DATOS A LA LISTA
                            keyboard.add(row);
                            row = new KeyboardRow();
                            row.add("2. PRODUCTOS");//Opcion2
                            keyboard.add(row);

                            kb.setKeyboard(keyboard);
                            texto_mensaje = "NO SE ENCONTRARON SUCURSALES. INTENTE MAS TARDE POR FAVOR.";
                            mensaje.setReplyMarkup(kb);
                            limpiar();
                        }

                    }
                    catch (SQLException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                //FIN PEDIR DATOS

                if(nombre != "" && direccion != "" && celular != "" && sucursal != "")
                {
                    if(comando.equals("CANCELAR"))
                    {
                        seccion = "INICIO";
                        keyboard.clear();
                        row.clear();
                        // AGREGAR LAS OPCIONES
                        row.add("1. EMPLEADOS");//Opcion1
                        // AGREGAR LOS DATOS A LA LISTA
                        keyboard.add(row);
                        row = new KeyboardRow();
                        row.add("2. PRODUCTOS");//Opcion2
                        keyboard.add(row);

                        kb.setKeyboard(keyboard);
                        texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
                        mensaje.setReplyMarkup(kb);
                        limpiar();
                        System.out.println("CANCELAR REGISTRO");
                    }
                    else if(comando.equals("GUARDAR"))
                    {
                        int resp=0;
                        resp = insertarEmpleado(nombre,direccion,celular,sucursal);
                        if(resp > 0)
                        {
                            texto_mensaje = "REGISTRO ÉXITOSO";
                            System.out.println("GUARDO");
                        }
                        else{
                            texto_mensaje = "ALGO SALIÓ MAL. INTENTE NUEVAMENTE POR FAVOR";
                            System.out.println("ERROR.");
                        }
                        seccion = "INICIO";
                        keyboard.clear();
                        row.clear();
                        // AGREGAR LAS OPCIONES
                        row.add("1. EMPLEADOS");//Opcion1
                        // AGREGAR LOS DATOS A LA LISTA
                        keyboard.add(row);
                        row = new KeyboardRow();
                        row.add("2. PRODUCTOS");//Opcion2
                        keyboard.add(row);

                        kb.setKeyboard(keyboard);
                        mensaje.setReplyMarkup(kb);
                        limpiar();
                    }
                    else{
                        texto_mensaje = "DATOS:\nNOMBRE: "+nombre+"\nDIRECCIÓN: "+direccion+"\nCELULAR: "+celular+"\nSUCURSAL: "+sucursal;

                        keyboard.clear();
                        row.clear();
                        row.add("GUARDAR");//opcion1
                        keyboard.add(row);
                        row = new KeyboardRow();
                        row.add("CANCELAR");//opcion1
                        keyboard.add(row);
                    }
                }
            }
            else if(seccion.equals("PRODUCTOS"))
            {
                if(codigo == "")
                {
                    row.add("1. CÓDIGO*");//opcion1
                    keyboard.add(row);
                }
                if(nombre == "")
                {
                    row = new KeyboardRow();
                    row.add("2. NOMBRE*");//Opcion2
                    keyboard.add(row);
                }
                if(descripcion == "")
                {
                    row = new KeyboardRow();
                    row.add("3. DESCRIPCIÓN");//Opcion3
                    keyboard.add(row);
                }
            }

            // SETTEAR LAS FILAS DEL KEYBOARD
            kb.setKeyboard(keyboard);
            // AGREGAR LOS BOTONES AL KEYBOARD
            mensaje.setReplyMarkup(kb);
            accion = "REGISTRAR";
        }
        else if(comando.equals("2. MODIFICAR"))
        {

        }
        else if(comando.equals("3. ELIMINAR"))
        {

        } if(comando.equals("4. MOSTRAR"))
        {

        }
        else if(comando.equals("5. LISTAR"))
        {
            if(seccion == "EMPLEADOS"){
                texto_mensaje = listaEmpleados();
            }
            else{
                texto_mensaje = listaEmpleados();
            }
        }

        mensaje.setText(texto_mensaje);
        // PREPARAR EL MENSAJE CON EL CHATID DEL USUARIO
        mensaje.setChatId(update.getMessage().getChatId());
        // EJECUTAR EL MENSAJE
        try {
            execute(mensaje);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("FIN");
    }

    public void limpiar()
    {
         codigo = "";
         nombre = "";
         direccion = "";
         celular = "";
         tipo = "";
         descripcion = "";
    }

    /**************
    * FIN SECCIÓN
    * *************/

    /***********************
    * FUNCIONES PRODUCTOS
    * **********************/

    public void listaProductos()
    {
        //OBTENER LA CONEXION A LA BD
        Conexion conexion = new Conexion();
        try{
            Connection con = conexion.obtenerConexion();

            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM inventario_db.productos";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                System.out.println(rs.getString("nombre"));
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
    }

    /****************
    * FIN FUNCIONES
    * **************/

    /*********************
    * FUNCIONES EMPLEADOS
    * *******************/
    public String listaEmpleados()
    {
        //OBTENER LA CONEXION A LA BD
        String lista = "LISTA DE EMPLEADOS:";
        lista +="\n====================";
        Conexion conexion = new Conexion();
        try{
            lista +="\nID | SUCURSAL | NOMBRE | CELULAR";
            lista +="\n=================================\n";
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            String sql = "SELECT e.*,s.nombre as sucursal FROM empleados e JOIN sucursales s on s.id = e.sucursal_id";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                lista += rs.getInt("id")+" | "+rs.getString("sucursal")+" | "+rs.getString("nombre")+" | "+rs.getString("celular")+"\n";
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
        return lista;
    }

    public int insertarEmpleado(String nom, String dir, String cel, String sucursal)
    {
        //OBTENER LA CONEXION A LA BD
        int resultado = 0;
        Conexion conexion = new Conexion();
        try{
            //OBTENER SUCURSAL_ID
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM sucursales WHERE nombre ='"+sucursal+"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int sucursal_id = rs.getInt("id");

            String tipo = "EMPLEADO";
            Date fecha = new Date();
            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fecha_actual = formato.format(fecha);

            sql = "INSERT INTO empleados(sucursal_id,nombre,direccion,celular,tipo,fecha_reg)" +
                    " VALUES("+sucursal_id+",'"+nom.toUpperCase()+"','"+dir.toUpperCase()+"','"+cel+"','"+tipo+"','"+fecha_actual+"')";
            PreparedStatement pstmt = con.prepareStatement(sql);
            resultado = pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
        return resultado;
    }

    public ResultSet sucursales()
    {
        //OBTENER LA CONEXION A LA BD
        Conexion conexion = new Conexion();
        ResultSet rs = null;
        try{
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM sucursales";
            rs = stmt.executeQuery(sql);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return rs;
    }
}
