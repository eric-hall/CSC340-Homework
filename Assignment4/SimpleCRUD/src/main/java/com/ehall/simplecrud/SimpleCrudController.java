package com.ehall.simplecrud;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SimpleCrudController implements Initializable {

    @FXML
    private TableView<MusicAlbum> tableView;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField artistInput;
    @FXML
    private TextField releaseYearInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameInput.setText(newValue.getName());
                artistInput.setText(newValue.getArtist());
                releaseYearInput.setText(Integer.toString(newValue.getReleaseYear()));
            } else { // If no row is selected, clear the input fields
                nameInput.clear();
                artistInput.clear();
                releaseYearInput.clear();
            }
        });

        // Force year input as numeric-only
        releaseYearInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                releaseYearInput.setText(newValue.replaceAll("\\D", ""));
            }
        });

        loadTestEntries(); // TODO: For assignment demo purposes only
    }

    private void loadTestEntries() {

        MusicAlbum[] albums = {
                new MusicAlbum("Audio, Video, Disco.", "Justice", 2011),
                new MusicAlbum("Homework", "Daft Punk", 1997),
                new MusicAlbum("Demon Days", "Gorillaz", 2005),
                new MusicAlbum("Rage Against The Machine", "Rage Against The Machine", 1992),
                new MusicAlbum("CASIOPEA", "CASIOPEA", 1972),
                new MusicAlbum("Mezzanine", "Massive Attack", 1998),
                new MusicAlbum("Can't Buy A Thrill", "Steely Dan", 1972),
                new MusicAlbum("Astro Lounge", "Smash Mouth", 1999),
                new MusicAlbum("Torches", "Foster The People", 2011),
                new MusicAlbum("Meddle", "Pink Floyd", 1971),
                new MusicAlbum("Hotel California", "Eagles", 1967),
        };

        ObservableList<MusicAlbum> tableItems = tableView.getItems();
        tableItems.addAll(albums);
    }

    @FXML
    private void addEntry() {

        MusicAlbum entry = getMusicAlbumFromInputFields();
        if (entry == null) {
            return;
        }

        // Add to table and clear the input fields
        tableView.getItems().add(entry);
        nameInput.clear();
        artistInput.clear();
        releaseYearInput.clear();
        tableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void updateEntry() {

        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }

        MusicAlbum entry = getMusicAlbumFromInputFields();
        if (entry == null) {
            return;
        }

        tableView.getItems().set(selectedIndex, entry);
    }

    @FXML
    private void removeEntry() {

        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }

        tableView.getItems().remove(selectedIndex);
        tableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void menuNewFile() {
        // Simply clear the table for now
        tableView.getItems().clear();
        nameInput.clear();
        artistInput.clear();
        releaseYearInput.clear();
    }

    @FXML
    private void menuOpenFile() {

        // Allow user the pick a file from their system
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Music Album File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if (file == null) {
            return;
        }

        try (Scanner reader = new Scanner(new FileInputStream(file))) {

            List<MusicAlbum> albums = new ArrayList<>();
            MusicAlbum album = new MusicAlbum();

            while (reader.hasNext()) { // TODO: Clean this up (serialize...?)
                String line = reader.nextLine();
                if (line.isEmpty()) {
                    albums.add(album);
                    album = new MusicAlbum();
                } else if (line.startsWith("Name: ")) {
                    album.setName(line.substring(6));
                } else if (line.startsWith("Artist: ")) {
                    album.setArtist(line.substring(8));
                } else if (line.startsWith("Release Year: ")) {
                    album.setReleaseYear(Integer.parseInt(line.substring(14)));
                }
            }

            tableView.getItems().setAll(albums);

        } catch (IOException e) {
            System.err.println("Error while saving data to the file. " + e.getMessage());
        }
    }

    @FXML
    private void menuSaveFile() {

        // Allow user the pick a file from their system
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Music Album File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (MusicAlbum album : tableView.getItems()) {
                writer.write("Name: " + album.getName() + "\n");
                writer.write("Artist: " + album.getArtist() + "\n");
                writer.write("Release Year: " + album.getReleaseYear() + "\n");
                writer.write("\n"); // Separate entries with another newline
            }
        } catch (IOException e) {
            System.err.println("Error while saving data to the file. " + e.getMessage());
        }
    }

    private MusicAlbum getMusicAlbumFromInputFields() {

        String name = nameInput.getText();
        String artist = artistInput.getText();
        String year = releaseYearInput.getText();

        if (name.isBlank() || artist.isBlank() || year.isBlank()) {
            return null;
        }

        return new MusicAlbum(name, artist, Integer.parseInt(year));
    }
}
