package ru.ifmo.lesson.spring.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.ifmo.lesson.Post;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        try {
            System.out.println("Все курсы");
            System.out.println(getCourses());


            System.out.println("\nКурсы по названию");
            System.out.println(getCourses("python"));


            System.out.println("\nКурсы (постранично)");
            getCourses(1, 2);


            System.out.println("\nРегистрация студента на курс");
            regStudent("New", getCourses("js").get(0).getId());

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static List<Course> getCourses() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = "http://localhost:8080/course";
        HttpUriRequest httpGet = new HttpGet(url);


        CloseableHttpResponse responseGet = httpClient.execute(httpGet);

        HttpEntity entityGet = responseGet.getEntity();
        String jsonGet = EntityUtils.toString(entityGet);

        ObjectMapper mapper = new ObjectMapper();

        CollectionType type = mapper
                .getTypeFactory()
                .constructCollectionType(ArrayList.class, Course.class);

        ArrayList<Course> courses = mapper.readValue(jsonGet, type);
        return courses;
    }

    public static List<Course> getCourses(String title) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = "http://localhost:8080/course/" + title;
        HttpUriRequest httpGet = new HttpGet(url);


        CloseableHttpResponse responseGet = httpClient.execute(httpGet);

        HttpEntity entityGet = responseGet.getEntity();
        String jsonGet = EntityUtils.toString(entityGet);

        ObjectMapper mapper = new ObjectMapper();

        CollectionType type = mapper
                .getTypeFactory()
                .constructCollectionType(ArrayList.class, Course.class);

        ArrayList<Course> courses = mapper.readValue(jsonGet, type);
        return courses;
    }

    public static void getCourses(int page, int size) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder("http://localhost:8080/course/params");
        builder.setParameter("page", String.valueOf(page))
                .setParameter("size", String.valueOf(size));

        System.out.println(builder.build());

        HttpUriRequest httpGet  = new HttpGet(builder.build());


        CloseableHttpResponse responseGet = httpClient.execute(httpGet);

        HttpEntity entityGet = responseGet.getEntity();
        String jsonGet = EntityUtils.toString(entityGet);

        System.out.println(jsonGet);
    }

    public static Student regStudent(String name, int courseId) throws IOException {
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setEmail(name.toLowerCase() + "@gmail.com");

        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = mapper.writeValueAsString(newStudent);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        String postUri = "http://localhost:8080/student";
        HttpPost httpPost = new HttpPost(postUri);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("student", jsonStr)); // данные 1
        params.add(new BasicNameValuePair("courseId", String.valueOf(courseId))); // данные 2

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse responsePost = httpClient.execute(httpPost);
        HttpEntity entityPost = responsePost.getEntity();
        String jsonPost = EntityUtils.toString(entityPost);
        System.out.println(jsonPost);

        Student student = mapper.readValue(jsonPost, Student.class);

        return student;
    }

}
