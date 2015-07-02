/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ROTA;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.Timer;

/**
 *
 * @author Ademilson
 */
public class MeuMapa extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createMap());
        scene.setFill(Color.rgb(230, 230, 230));
        stage.setScene(scene);
        stage.setTitle("Java Conference Tour");
        stage.setHeight(450);
        stage.setWidth(700);
        stage.show();
    }

    private TextField txfOrigem = new TextField();
    private TextField txfDestino= new TextField();

    private BorderPane createMap() throws IOException {
        BorderPane inner = new BorderPane();
        
        txfOrigem.setPrefColumnCount(30);
        txfDestino.setPrefColumnCount(30);
        

        Button btnPesquisar = new Button("Criar Rota");
        btnPesquisar.setFocusTraversable(false);
        btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                metodosJS("carregarNoMapa('" + txfOrigem.getText() + "');");
            }
        });
        
        

        
        inner.setTop(FXMLLoader.load(getClass().getResource("Filtro.fxml")));
//        inner.setTop(new BorderPane(null, txfOrigem, btnPesquisar, txfDestino, null));

        inner.setCenter(createWebView());
        inner.setPadding(new Insets(2, 2, 2, 2));
        return inner;
    }


    WebEngine engine;

    private WebView createWebView() {
        WebView webView = new WebView();
        engine = webView.getEngine();
        webView.getEngine().load(getClass().getResource("content.html").toString());
        return webView;
    }

    private Object metodosJS(String metodo) {
        return engine.executeScript(metodo);
    }

}
