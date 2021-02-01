package Handlers;

import Entities.City;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.StringTokenizer;

//Обработчик запросов POST
public class PostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue=null;
        if("POST".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handlePostRequest(httpExchange);
        }
        handleResponse(httpExchange,requestParamValue);
    }

    // Метод для декодирования запроса
    private String handlePostRequest(HttpExchange httpExchange) throws JsonProcessingException {
        /*
        Здесь я использовал обычные классы и листы для хранения данных. В начале я хотел использовать JDBC библиотеки для соеденение с БД.
        Но у меня возникли проблемы с драйверами и библиотекой.
         */
        String response = null;
        ArrayList<City> reports = new ArrayList<City>();
        reports.add(new City("ALA","Sunny","GMT+6"));
        reports.add(new City("ATY","Rain","GMT+5"));
        InputStream inputStream = httpExchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String request = textBuilder.toString();
        request = request.replace("}","");
        request = request.replace("{","");
        StringTokenizer stringTokenizer = new StringTokenizer(request,":");
        String argument=stringTokenizer.nextToken();
        String value = stringTokenizer.nextToken();
        if(!argument.equals("zipCode")){
            return null;
        }
        for (City c: reports) {
            if(c.getZipCode().equals(value)){
                response=c.getTimeZone();
            }
        }

        return response;
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
