package Handlers;

import Entities.City;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

//Обработчик запросов GET
public class GetHandler implements HttpHandler {
    // Main handle method
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue=null;
        if("GET".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(httpExchange);
        }
        handleResponse(httpExchange,requestParamValue);
    }

    // Метод для декодирования запроса
    private String handleGetRequest(HttpExchange httpExchange){
        /*
        Здесь я использовал обычные классы и листы для хранения данных. В начале я хотел использовать JDBC библиотеки для соеденение с БД.
        Но у меня возникли проблемы с драйверами и библиотекой.
         */
        ArrayList<City> reports = new ArrayList<City>();
        reports.add(new City("ALA","Sunny","GMT+6"));
        reports.add(new City("ATY","Rain","GMT+5"));
        String query = httpExchange.getRequestURI().getRawQuery();
        StringTokenizer tokenizer = new StringTokenizer(query,"=");
        String argument = tokenizer.nextToken();
        String value = tokenizer.nextToken();
        String respText = "";
        if(argument.equals("code")){
            for (City w: reports){
                if (w.getZipCode().equals(value)){
                    respText=w.getWeatherReport();
                    break;
                }
            }
        }else{
            respText="error";
        }
        return respText;
    }

    //Создание ответа и его отправка
    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        if(requestParamValue.equals("error")|| requestParamValue == null){
            httpExchange.sendResponseHeaders(405, -1);
        }else{
            httpExchange.sendResponseHeaders(200, requestParamValue.length());
            OutputStream output = httpExchange.getResponseBody();
            output.write(requestParamValue.getBytes());
            output.flush();
            output.close();
        }

    }
}
