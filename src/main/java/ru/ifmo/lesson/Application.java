package ru.ifmo.lesson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        Pojo pojo = new Pojo(1);
        System.out.println(pojo);

        // https://jsonplaceholder.typicode.com/posts?_limit=10
        // необходим для отправки запросов и получения ответов
        // можно в try with resources
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // объект запроса
        String url = "https://jsonplaceholder.typicode.com/posts?_limit=10";
        HttpUriRequest httpGet = new HttpGet(url);

        // объект ответа
        // можно в try with resources
        CloseableHttpResponse responseGet = httpClient.execute(httpGet);
        // сервер формирует ответное сообшение, которое состоит из:
        // статус ответа, например 200, 404
        // заголовки
        // тело сообщения, пустое или с данными

        HttpEntity entityGet = responseGet.getEntity(); // тело сообщения
        String jsonGet = EntityUtils.toString(entityGet);
        System.out.println(jsonGet);
        ObjectMapper mapper = new ObjectMapper();

        // коллекция объектов из json строки
        CollectionType type = mapper
                .getTypeFactory()
                .constructCollectionType(ArrayList.class, Post.class);

        ArrayList<Post> posts = mapper.readValue(jsonGet, type);
        System.out.println(posts);




        // CloseableHttpClient, CloseableHttpResponse необходимо закрыть,
        // если он созданы не в try with resources


        // Post запрос и передача даных в теле сообщения
        String postUri = "https://jsonplaceholder.typicode.com/posts";
        HttpPost httpPost = new HttpPost(postUri);

        // добавление параметров в тело сообщения (для post, put, patch запросов)
        // списко параметров
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("userId", "45")); // данные 1
        params.add(new BasicNameValuePair("title", "Java 16")); // данные 2
        params.add(new BasicNameValuePair("body", "Records type")); // данные 2

        // параметры должны быть закодированы
        // и добавлены в тело сообщения
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse responsePost = httpClient.execute(httpPost);
        HttpEntity entityPost = responsePost.getEntity();
        String jsonPost = EntityUtils.toString(entityPost);
        System.out.println(jsonPost);

        Post post = mapper.readValue(jsonPost, Post.class);
        System.out.println(post);

        // postgreSql
        // pgAdmin


        // get запрос - для получения данных HttpGet
        // post запрос - добавление новой инфомации от клиента HttpPost
        // put и patch запросы - обновление информации
        // delete запрос - удаление информации

        // сообщение от клиента на сервер состоит из:
        // 1. строки запроса
        // 2. заголовки - дополнительная информация о сообщении
        // 3. тело сообщения (может быть пустым или содержать данные)

        // клиент может передать данные на сервер
        // 1. в строке запроса после ?
        // в парах имя=значение или ?имя=значение&имя2=значение
        // get запрос или delete запрос
        // 2. в теле сообщения в парах имя и значение
        // post запрос, put запрос или patch запрос

        // Json в объект
        Post newPost = new Post();
        newPost.setId(34);
        newPost.setUserId(12);
        newPost.setTitle("новая запись");
        newPost.setBody("текст записи");

        // запись объекта в json строку
        String jsonStr = mapper.writeValueAsString(newPost);
        System.out.println(jsonStr);

        // запись в файл
        mapper.writeValue(new File("file.json"), newPost);
        // чтение из файла
        Post fromFile = mapper.readValue(new File("file.json"), Post.class);



    }
}
