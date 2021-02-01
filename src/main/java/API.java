import Handlers.GetHandler;
import Handlers.PostHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

//Основной класс для запущенного сервера
public class API {
    public static void main(String[] args) throws IOException {
        // Иницилизация сервера
        int port=8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port),0);
        // Добавление обработчиков запросов на сервер
        server.createContext("/api/weather", new GetHandler());
        server.createContext("/api/time", new PostHandler());
        //Запуск сервера
        server.setExecutor(null);
        server.start();
    }
}
