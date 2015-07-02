/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mewMapsPersonalizado;

import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private TextField txfPesquisa = new TextField();

    private BorderPane createMap() {
        BorderPane inner = new BorderPane();

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setFocusTraversable(false);
        btnPesquisar.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                metodosJS("carregarNoMapa('" + txfPesquisa.getText() + "');");
            }
        });

        txfPesquisa.setPrefColumnCount(30);
        btnPesquisar.setGraphic(txfPesquisa);
        
        inner.setTop(new BorderPane(btnPesquisar));

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
