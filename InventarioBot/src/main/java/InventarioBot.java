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

    public int id= 0;
    public String seccion = "";
    public String accion = "";
    public String campo = "";
    public String codigo = "";
    public String codigo_modificar = "";
    public String sucursal = "";
    public String nombre = "";
    public String stock = "";
    public String direccion = "";
    public String celular = "";
    public String tipo = "";
    public String descripcion = "";
    public boolean sw_mensajeMempleados = false;
    public boolean sw_mensajeMProductos = false;

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
        return "Iventario_Bot";
    }

   public String getBotToken() {
        return "950532405:AAHjQ8CsJoB8h3yO7rJaKSJAEEWnrZSkUI0";
    }
   /* public String getBotToken() {
        return "1011018114:AAF75PTT1qglCByMO0RpdeVmYQuunu95cbw";
    }*/

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

        // RESPUESTA
        if(comando.equals("/almacen"))
        {
            seccion = "INICIO";
            accion = "";
            campo = "";
            /*keyboard.clear();
            row.clear();*/
            // AGREGAR LAS OPCIONES INICIO
            keyboard = inicio();
            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("1. EMPLEADOS") || comando.equals("/EMPLEADOS")) {
            seccion = "EMPLEADOS";
            accion = "";
            campo = "";
            limpiar();
            // AGREGAR LAS OPCIONES ACCIONES
            keyboard = acciones();
            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("2. PRODUCTOS") || comando.equals("/PRODUCTOS")){
            seccion = "PRODUCTOS";
            accion = "";
            campo = "";
            limpiar();
            // AGREGAR LAS OPCIONES ACCIONES
            keyboard = acciones();
            KeyboardRow row = new KeyboardRow();
            row.add("6. PRODUCTOS SUCURSAL");
            keyboard.add(row);
            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("1. REGISTRAR") || accion.equals("REGISTRAR"))
        {
            // AGREGAR LOS DATOS A LA LISTA
            if(seccion.equals("EMPLEADOS")) {
                /*******************************
                 *             EMPLEADOS
                 * ****************************/
                if(accion.equals(""))
                {
                    texto_mensaje = "DATOS QUE SE NECESITAN:\n1. NOMBRE*\n2. DIRECCIÓN*\n3. CELULAR*\n4. SUCURSAL*\n===============================\nDEBE IR ELIGIENDO LOS CAMPOS(LOS CAMPOS CON * SON OBLIGATORIOS)";
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

                // VALIDAR QUE OPCIONES SE MOSTRARAN EN EL KEYBOARD
                keyboard = datosEmpleados();

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
                    if(res != null)
                    {
                        // OBTENER EL KEYBOARD DE SUCURSALES
                        keyboard = keyBoardsucursales(res);
                    }
                    if(keyboard.size() == 0)
                    {
                        // SI NO TIENE ELEMENTOS MOSTRAR ERROR
                        seccion = "INICIO";
                        keyboard = inicio();
                        kb.setKeyboard(keyboard);
                        texto_mensaje = "NO SE ENCONTRARON SUCURSALES. INTENTE MAS TARDE POR FAVOR.";
                        mensaje.setReplyMarkup(kb);
                        limpiar();
                    }
                }
                //FIN PEDIR DATOS

                if(nombre != "" && direccion != "" && celular != "" && sucursal != "")
                {
                    if(comando.equals("CANCELAR"))
                    {
                        seccion = "INICIO";
                        keyboard = inicio();
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
                        keyboard = inicio();
                        kb.setKeyboard(keyboard);
                        mensaje.setReplyMarkup(kb);
                        limpiar();
                        seccion = "";
                        accion = "";
                        campo = "";
                    }
                    else{
                        texto_mensaje = "DATOS\n=======\nNOMBRE: "+nombre+"\nDIRECCIÓN: "+direccion+"\nCELULAR: "+celular+"\nSUCURSAL: "+sucursal;
                        //OPCIONES GUARDAR Y CANCELAR
                        keyboard = opcionesGC();
                    }
                }
            }
            else if(seccion.equals("PRODUCTOS"))
            {
                /*******************************
                 *             PRODUCTOS
                 * ****************************/
                if(accion.equals(""))
                {
                    texto_mensaje = "DATOS QUE SE NECESITAN:\n1. CÓDIGO*\n2. NOMBRE*\n3. DESCRIPCIÓN\n===============================\nDEBE IR ELIGIENDO LOS CAMPOS(LOS CAMPOS CON * SON OBLIGATORIOS)";
                }
                // ASIGNAR LA ACCION ACTUAL
                accion = "REGISTRAR";

                // ASIGNAR CAMPOS
                if(!comando.equals("1. CÓDIGO") && !comando.equals("2. NOMBRE") && !comando.equals("3. DESCRIPCIÓN") && !comando.equals("GUARDAR")  && !comando.equals("CANCELAR"))
                {
                    if(campo.equals("codigo"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(nombre != "" && descripcion != "")
                        {
                            texto_mensaje = "CORRECTO!";
                        }
                        codigo = comando;
                    }
                    else if(campo.equals("nombre"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(codigo != "" && descripcion != "") {
                            texto_mensaje = "CORRECTO!";
                        }
                        nombre = comando;
                    }
                    else if(campo.equals("descripcion"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(codigo != "" && nombre != ""){
                            texto_mensaje = "CORRECTO!";
                        }
                        descripcion = comando;
                    }
                }
                //FIN ASIGANAR CAMPOS

                // VALIDAR QUE OPCIONES SE MOSTRARAN EN EL KEYBOARD
                keyboard = datosProductos();

                // PEDIR DATOS
                if(comando.equals("1. CÓDIGO"))
                {
                    texto_mensaje = "INGRESE EL CÓDIGO DEL PRODUCTO:";
                    campo = "codigo";
                }
                else if(comando.equals("2. NOMBRE"))
                {
                    texto_mensaje = "INGRESE EL NOMBRE DEL PRODUCTO:";
                    campo = "nombre";
                }
                // FIN PEDIR DATOS OBLIGATORIOS

                if(codigo != "" && nombre != "")
                {
                    if(comando.equals("CANCELAR"))
                    {
                        seccion = "INICIO";
                        keyboard = inicio();
                        kb.setKeyboard(keyboard);
                        texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
                        mensaje.setReplyMarkup(kb);
                        limpiar();
                        System.out.println("CANCELAR REGISTRO");
                    }
                    else if(comando.equals("GUARDAR"))
                    {
                        int resp=0;
                        resp = insertarProducto(codigo,nombre,descripcion);
                        if(resp > 0)
                        {
                            texto_mensaje = "REGISTRO ÉXITOSO";
                            System.out.println("GUARDO");
                        }
                        else{
                            texto_mensaje = "ALGO SALIÓ MAL. INTENTE NUEVAMENTE POR FAVOR. REVISE QUE EL CÓDIGO NO ESTE DUPLICADO";
                            System.out.println("ERROR.");
                        }
                        seccion = "INICIO";
                        keyboard = inicio();
                        kb.setKeyboard(keyboard);
                        mensaje.setReplyMarkup(kb);
                        limpiar();
                        seccion = "";
                        accion = "";
                        campo = "";
                    }
                    else{
                        texto_mensaje = "DATOS\n=======\nCÓDIGO: "+codigo+"\nNOMBRE: "+nombre+"\nDESCRIPCIÓN: "+descripcion;
                        //OPCIONES GUARDAR Y CANCELAR
                        keyboard = opcionesGC();
                        if(descripcion == "")
                        {
                            KeyboardRow row = new KeyboardRow();
                            row.add("3. DESCRIPCIÓN");
                            keyboard.add(row);
                        }
                    }
                }

                // DATO OPCIONAL
                if(comando.equals("3. DESCRIPCIÓN"))
                {
                    texto_mensaje = "INGRESE LA DESCRIPCIÓN DEL PRODUCTO:";
                    campo = "descripcion";
                }
                //FIN PEDIR DATO OPCIONAL

            }

            // SETTEAR LAS FILAS DEL KEYBOARD
            kb.setKeyboard(keyboard);
            // AGREGAR LOS BOTONES AL KEYBOARD
            mensaje.setReplyMarkup(kb);
            accion = "REGISTRAR";
        }
        else if(comando.equals("2. MODIFICAR") || accion.equals("MODIFICAR") || accion.equals("MODIFICARP"))
        {
            if(accion == "")
            {
                accion = "MODIFICAR";
            }

            if(seccion.equals("EMPLEADOS"))
            {
                /**********************
                 *       EMPLEADOS
                 * *******************/
                // ASIGNAR LA ACCION A ID PARA SOLICITAR QUE SE INGRESE
                if(id == 0)
                {
                    texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE DESEA MODIFICAR POR FAVOR";
                    accion = "ID";
                }

                // ASIGNAR CAMPOS
                if(!comando.equals("1. NOMBRE") && !comando.equals("2. DIRECCIÓN") && !comando.equals("3. CELULAR") && !comando.equals("4. SUCURSAL")  && !comando.equals("GUARDAR")  && !comando.equals("CANCELAR"))
                {
                    int respuesta = 0;
                    if(campo.equals("nombre"))
                    {
                        respuesta = modificarEmpleado("nombre",id,comando);
                        if(respuesta > 0)
                        {
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                        }
                        else{
                            texto_mensaje = "ERROR ALGO SALIÓ MAL INTENTE NUEVAMENTE PORFAVOR";
                        }
                        nombre = "";
                    }
                    else if(campo.equals("direccion"))
                    {
                        respuesta = modificarEmpleado("direccion",id,comando);
                        if(respuesta > 0)
                        {
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                        }
                        else{
                            texto_mensaje = "ERROR ALGO SALIÓ MAL INTENTE NUEVAMENTE PORFAVOR";
                        }
                        direccion = "";
                    }
                    else if(campo.equals("celular"))
                    {
                        respuesta = modificarEmpleado("celular",id,comando);
                        if(respuesta > 0)
                        {
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                        }
                        else{
                            texto_mensaje = "ERROR ALGO SALIÓ MAL INTENTE NUEVAMENTE PORFAVOR";
                        }
                        texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                        celular = "";
                    }
                    else if(campo.equals("sucursal"))
                    {
                        respuesta = modificarEmpleado("sucursal_id",id,comando);
                        if(respuesta > 0)
                        {
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                        }
                        else{
                            texto_mensaje = "ERROR ALGO SALIÓ MAL INTENTE NUEVAMENTE PORFAVOR";
                        }
                        texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                        sucursal = "";
                    }
                    keyboard = datosEmpleados();
                    kb.setKeyboard(keyboard);
                    mensaje.setReplyMarkup(kb);
                }
                //FIN ASIGANAR CAMPOS

                // PEDIR DATOS
                if(comando.equals("1. NOMBRE"))
                {
                    texto_mensaje = "INGRESE EL NUEVO NOMBRE DEL EMPLEADO:";
                    campo = "nombre";
                }
                else if(comando.equals("2. DIRECCIÓN"))
                {
                    texto_mensaje = "INGRESE LA NUEVA DIRECCIÓN DEL EMPLEADO:";
                    campo = "direccion";
                }
                else if(comando.equals("3. CELULAR"))
                {
                    texto_mensaje = "INGRESE EL NUEVO CELULAR DEL EMPLEADO:";
                    campo = "celular";
                }
                else if(comando.equals("4. SUCURSAL"))
                {
                    texto_mensaje = "SELECCIONE LA NUEVA SUCURSAL:";
                    campo = "sucursal";
                    ResultSet res = sucursales();
                    if(res != null)
                    {
                        // OBTENER EL KEYBOARD DE SUCURSALES
                        keyboard = keyBoardsucursales(res);
                        kb.setKeyboard(keyboard);
                        mensaje.setReplyMarkup(kb);
                    }
                    if(keyboard.size() == 0)
                    {
                        // SI NO TIENE ELEMENTOS MOSTRAR ERROR
                        seccion = "INICIO";
                        keyboard = inicio();
                        kb.setKeyboard(keyboard);
                        texto_mensaje = "NO SE ENCONTRARON SUCURSALES. INTENTE MAS TARDE POR FAVOR.";
                        mensaje.setReplyMarkup(kb);
                        limpiar();
                    }
                }
                //FIN PEDIR DATOS
            }
            else if(seccion.equals("PRODUCTOS"))
            {
                /**********************
                *       PRODUCTOS
                * *******************/
                if(accion.equals("MODIFICARP"))
                {
                    // ASIGNAR CAMPOS
                    if(!comando.equals("1. CÓDIGO") && !comando.equals("2. NOMBRE") && !comando.equals("3. DESCRIPCIÓN") && !comando.equals("GUARDAR")  && !comando.equals("CANCELAR"))
                    {
                        System.out.println(campo + " | CAMPO");

                        if(codigo_modificar == "")
                        {
                            codigo_modificar = comando;
                            System.out.println(codigo_modificar);
                            texto_mensaje = muestraProducto(codigo_modificar);
                            if(texto_mensaje != "")
                            {
                                texto_mensaje += "\n=================================";
                                texto_mensaje += "\nSELECIONE UN CAMPO PARA MODIFICAR";
                            }
                            else{
                                codigo_modificar = "";
                                texto_mensaje = "NO SE ENCONTRÓ NINGUN PRODUCTO CON ESE ID/CÓDIGO. INTENTE NUEVAMENTE.";
                                accion = "MODIFICAR";
                            }
                        }

                        int respuesta = 0;
                        if(campo.equals("codigo"))
                        {
                            respuesta = modificarProducto("codigo",codigo_modificar,comando);
                            if(respuesta > 0)
                            {
                                texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            }
                            else{
                                texto_mensaje = "ERROR ALGO SALIÓ MAL INTENTE NUEVAMENTE PORFAVOR";
                            }
                            codigo = "";
                        }
                        else if(campo.equals("nombre"))
                        {
                            respuesta = modificarProducto("nombre",codigo_modificar,comando);
                            if(respuesta > 0)
                            {
                                texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            }
                            else{
                                texto_mensaje = "ERROR ALGO SALIÓ MAL INTENTE NUEVAMENTE PORFAVOR";
                            }
                            nombre = "";
                        }
                        else if(campo.equals("descripcion"))
                        {
                            respuesta = modificarProducto("descripcion",codigo_modificar,comando);
                            if(respuesta > 0)
                            {
                                texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            }
                            else{
                                texto_mensaje = "ERROR ALGO SALIÓ MAL INTENTE NUEVAMENTE PORFAVOR";
                            }
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            descripcion = "";
                        }
                        keyboard = datosProductos();
                        kb.setKeyboard(keyboard);
                        mensaje.setReplyMarkup(kb);
                    }
                    //FIN ASIGANAR CAMPOS

                    // PEDIR DATOS
                    if(comando.equals("1. CÓDIGO"))
                    {
                        texto_mensaje = "INGRESE EL NUEVO CÓDIGO DEL PRODUCTO:";
                        campo = "codigo";
                    }
                    else if(comando.equals("2. NOMBRE"))
                    {
                        texto_mensaje = "INGRESE EL NUEVO NOMBRE DEL PRODUCTO:";
                        campo = "nombre";
                    }
                    else if(comando.equals("3. DESCRIPCIÓN"))
                    {
                        texto_mensaje = "INGRESE LA NUEVA DESCRIPCIÓN DEL PRODUCTO:";
                        campo = "descripcion";
                    }
                    //FIN PEDIR DATOS
                }
                else{
                    texto_mensaje = "INGRESE EL ID/CÓDIGO DEL PRODUCTO QUE QUIERE MODIFICAR";
                    accion = "MODIFICARP";
                }
            }
        }
        else if(comando.equals("3. ELIMINAR") || accion.equals("ELIMINAR"))
        {
            if(seccion == "EMPLEADOS"){
                if(accion.equals("ELIMINAR"))
                {
                    int respuesta = 0;
                    respuesta = eliminaEmpleado(Integer.parseInt(comando));
                    if(respuesta > 0)
                    {
                        texto_mensaje = "EMPLEADO ELIMINADO CON ÉXITO!";
                    }
                    else{
                        texto_mensaje = "NO SE ENCONTRÓ NINGUN EMPLEADO CON ESE ID.\nPOR FAVOR INGRESE OTRO ID";
                    }
                }
                else{
                    texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE QUIERE ELIMINAR";
                    accion = "ELIMINAR";
                }
            }
            else{
                if(accion.equals("ELIMINAR"))
                {
                    int respuesta = 0;
                    respuesta = eliminaProducto(comando);
                    if(respuesta > 0)
                    {
                        texto_mensaje = "PRODUCTO ELIMINADO CON ÉXITO!";
                    }
                    else{
                        texto_mensaje = "NO SE PUDO ELIMINAR O NO SE ENCONTRÓ NINGÚN PRODUCTO CON ESE ID/CÓDIGO.\nPOR FAVOR INGRESE OTRO ID/CÓDIGO";
                    }
                }
                else{
                    texto_mensaje = "INGRESE EL ID/CÓDIGO DEL PRODUCTO QUE QUIERE ELIMINAR";
                    accion = "ELIMINAR";
                }
            }
        } if(comando.equals("4. MOSTRAR") || accion.equals("MOSTRAR"))
        {
            if(seccion == "EMPLEADOS"){
                if(accion.equals("MOSTRAR"))
                {
                    texto_mensaje = muestraEmpleado(Integer.parseInt(comando));
                    if(texto_mensaje == "")
                    {
                        texto_mensaje = "NO SE ENCONTRÓ NINGUN EMPLEADO CON ESE ID. POR FAVOR INGRESE OTRO ID";
                    }
                    else{
                        accion = "";
                    }
                }
                else{
                    texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE QUIERE VER";
                    accion = "MOSTRAR";
                }
            }
            else{
                if(accion.equals("MOSTRAR"))
                {
                    texto_mensaje = muestraProducto(comando);
                    if(texto_mensaje == "")
                    {
                        texto_mensaje = "NO SE ENCONTRÓ NINGUN PRODUCTO CON ESE ID/CÓDIGO. POR FAVOR INGRESE OTRO ID/CÓDIGO";
                    }
                    else{
                        accion = "";
                    }
                }
                else{
                    texto_mensaje = "INGRESE EL ID/CÓDIGO DEL PRODUCTO QUE QUIERE VER";
                    accion = "MOSTRAR";
                }
            }
        }
        else if(comando.equals("5. LISTAR"))
        {
            if(seccion == "EMPLEADOS"){
                texto_mensaje = listaEmpleados();
            }
            else{
                texto_mensaje = listaProductos();
            }
        }
        else if(comando.equals("6. PRODUCTOS SUCURSAL"))
        {
            texto_mensaje = listaProductosSucursal();
        }
        else if(accion.equals("ID"))
        {
            if(!comando.equals("2. MODIFICAR"))
            {
                id = Integer.parseInt(comando);
                texto_mensaje = muestraEmpleado(id);
                if(texto_mensaje != "")
                {
                    texto_mensaje += "\n=======================================";
                    texto_mensaje += "\nSELECCIONE EL DATO QUE DESEA MODIFICAR";
                    texto_mensaje += "\n=======================================";
                    keyboard = datosEmpleados();
                    kb.setKeyboard(keyboard);
                    mensaje.setReplyMarkup(kb);
                    accion = "MODIFICAR";
                }
                else{
                    texto_mensaje += "\nNO SE ENCONTRO NINGUN EMPLEADO CON ESE ID. POR FAVOR INTENTE CON OTRO ID";
                }
            }
            else{
                texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE DESEA MODIFICAR POR FAVOR";
            }
        }
        else if(comando.equals("/start"))
        {
            texto_mensaje = "BIENVENIDO "+ update.getMessage().getFrom().getFirstName().toUpperCase();
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
        codigo_modificar = "";
        nombre = "";
        direccion = "";
        celular = "";
        tipo = "";
        descripcion = "";
        id = 0;
        stock = "";
        sucursal = "";
    }

    /**************
    * FIN SECCIÓN
    * *************/

    public List<KeyboardRow> inicio()
    {
        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();
        row.add("1. EMPLEADOS");//Opcion1
        // AGREGAR LOS DATOS A LA LISTA
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("2. PRODUCTOS");//Opcion2
        keyboard.add(row);
        return keyboard;
    }

   /* public List<KeyboardRow> empleados()
    {

    }

    public List<KeyboardRow> productos()
    {

    }*/

    public List<KeyboardRow> keyBoardsucursales(ResultSet r)
    {
        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();
        try{
            while(r.next())
            {
                row = new KeyboardRow();
                row.add(r.getString("nombre"));//sucursal
                keyboard.add(row);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return keyboard;
    }

    public List<KeyboardRow> opcionesGC()
    {
        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();
        row.add("GUARDAR");//opcion1
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("CANCELAR");//opcion1
        keyboard.add(row);
        return keyboard;
    }

    public List<KeyboardRow> datosEmpleados()
    {
        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();
        System.out.println(nombre);
        System.out.println(direccion);
        System.out.println(celular);
        System.out.println(sucursal);

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
        return keyboard;
    }

    public List<KeyboardRow> datosProductos()
    {
        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();
        if(codigo == "")
        {
            row.add("1. CÓDIGO");//opcion1
            keyboard.add(row);
        }
        if(nombre == "")
        {
            row = new KeyboardRow();
            row.add("2. NOMBRE");//Opcion2
            keyboard.add(row);
        }
        if(descripcion == "")
        {
            row = new KeyboardRow();
            row.add("3. DESCRIPCIÓN");//Opcion3
            keyboard.add(row);
        }
        return keyboard;
    }

    public List<KeyboardRow> acciones()
    {
        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();
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
        return keyboard;
    }

    /***********************
    * FUNCIONES PRODUCTOS
    * **********************/

    public int insertarProducto(String codigo, String nombre, String descripcion)
    {
        //OBTENER LA CONEXION A LA BD
        int resultado = 0;
        Conexion conexion = new Conexion();
        String sql = "";
        try{
            //OBTENER SUCURSAL_ID
            Connection con = conexion.obtenerConexion();
            /* Statement stmt = con.createStatement();
            sql = "SELECT * FROM sucursales WHERE nombre ='"+sucursal+"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int sucursal_id = rs.getInt("id");*/

            Date fecha = new Date();
            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fecha_actual = formato.format(fecha);

            sql = "INSERT INTO productos(codigo,nombre,descripcion,fecha_reg)" +
                    " VALUES('"+codigo.toUpperCase()+"','"+nombre.toUpperCase()+"','"+descripcion.toUpperCase()+"','"+fecha_actual+"')";
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

    public String muestraProducto(String id)
    {
        //OBTENER LA CONEXION A LA BD
        String lista = "DATOS DEL PRODUCTO:";
        lista +="\n====================\n";
        Conexion conexion = new Conexion();
        try{
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM productos WHERE id = '"+id+"' OR codigo = '"+id+"';";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                String descripcion ="";
                if(rs.getString("descripcion") != null && rs.getString("descripcion") != "")
                {
                    descripcion = rs.getString("descripcion");
                }
                else{
                    descripcion = "S/D";
                }
                lista += "ID: "+rs.getInt("id")+"\nCÓDIGO: "+rs.getString("codigo")+"\nNOMBRE: "+rs.getString("nombre")+"\nDESCRIPCIÓN: "+descripcion;
            }
            else{
                lista = "";
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
        return lista;
    }

    public int modificarProducto(String columna, String cod, String dato){
        int resultado = 0;
        Conexion conexion = new Conexion();
        String valor = "";
        String sql = "";
        try{
            //OBTENER SUCURSAL_ID
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            valor = dato;
            sql = "UPDATE productos SET "+columna+"='"+valor.toUpperCase()+"' WHERE codigo = '"+cod+"';";
            if(isNumeric(dato))
            {
                sql = "UPDATE productos SET "+columna+"='"+valor.toUpperCase()+"' WHERE id = "+cod+";";
            }
            System.out.println("SQL: "+sql);
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

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public int eliminaProducto(String id)
    {
        //OBTENER LA CONEXION A LA BD
        int resultado = 0;
        Conexion conexion = new Conexion();
        String sql = "";
        try{
            Connection con = conexion.obtenerConexion();

            /*Statement stmt = con.createStatement();
            sql = "SELECT * FROM producto_sucursal";
            ResultSet rs = stmt.executeQuery(sql);*/
            sql = "DELETE FROM productos WHERE id = '"+id+"' OR codigo = '"+id+"';";
            PreparedStatement pstmt = con.prepareStatement(sql);
            resultado = pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
        return resultado;
    }

    public String listaProductos()
    {
        //OBTENER LA CONEXION A LA BD
        Conexion conexion = new Conexion();
        String lista = "LISTA DE PRODUCTOS:";
        lista +="\n====================";

        try{
            lista +="\nID | CÓDIGO | NOMBRE | DESCRIPCIÓN";
            lista +="\n=================================\n";
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM productos";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                String descripcion ="";
                if(rs.getString("descripcion") != null && rs.getString("descripcion") != "")
                {
                    descripcion = rs.getString("descripcion");
                }
                else{
                    descripcion = "S/D";
                }
                lista += rs.getInt("id")+" | "+rs.getString("codigo")+" | "+rs.getString("nombre")+" | "+descripcion+"\n";
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
        return lista;
    }

    public String listaProductosSucursal()
    {
        //OBTENER LA CONEXION A LA BD
        Conexion conexion = new Conexion();
        String lista = "LISTA DE PRODUCTOS:";
        lista +="\n====================";

        try{
            lista +="\nID | CÓDIGO | SUCURSAL | NOMBRE | STOCK";
            lista +="\n=================================\n";
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            String sql = "SELECT p.*, ps.stock, s.nombre as sucursal" +
                    " FROM productos p JOIN producto_sucursal ps on ps.producto_id = p.id " +
                    "JOIN sucursales s on s.id = ps.sucursal_id ORDER BY s.nombre ASC";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                lista += rs.getInt("id")+" | "+rs.getString("codigo")+" | "+rs.getString("sucursal")+" | "+rs.getString("nombre")+" | "+rs.getInt("stock")+"\n";
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
        return lista;
    }

    /****************
    * FIN FUNCIONES
    * **************/

    /*********************
    * FUNCIONES EMPLEADOS
    * *******************/
    public String muestraEmpleado(int id)
    {
        //OBTENER LA CONEXION A LA BD
        String lista = "DATOS DEL EMPLEADO:";
        lista +="\n====================\n";
        Conexion conexion = new Conexion();
        try{
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            String sql = "SELECT e.*,s.nombre as sucursal FROM empleados e JOIN sucursales s on s.id = e.sucursal_id WHERE e.id = "+id+";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                lista += "ID: "+rs.getInt("id")+"\nNOMBRE: "+rs.getString("nombre")+"\nDIRECCIÓN: "+rs.getString("direccion")+"\nCELULAR: "+rs.getString("celular")+"\nSUCURSAL: "+rs.getString("sucursal");
            }
            else{
                lista = "";
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
        return lista;
    }

    public int eliminaEmpleado(int id)
    {
        //OBTENER LA CONEXION A LA BD
        int resultado = 0;
        Conexion conexion = new Conexion();
        try{
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM empleados WHERE id = "+id+";";
            PreparedStatement pstmt = con.prepareStatement(sql);
            resultado = pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        conexion.CerrarConexion();
        return resultado;
    }

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

    public int modificarEmpleado(String columna, int id, String dato)
    {
        int resultado = 0;
        Conexion conexion = new Conexion();
        String valor = "";
        String sql = "";
        try{
            //OBTENER SUCURSAL_ID
            Connection con = conexion.obtenerConexion();
            Statement stmt = con.createStatement();
            valor = dato;
            if(columna.equals("sucursal_id"))
            {
                sql = "SELECT * FROM sucursales WHERE nombre ='"+dato+"'";
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();
                int sucursal_id = rs.getInt("id");
                valor = sucursal_id+"";
            }
            sql = "UPDATE empleados SET "+columna+"='"+valor.toUpperCase()+"' WHERE id = "+id+";";
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
