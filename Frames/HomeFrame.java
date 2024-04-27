package Frames;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

interface SaveListener {
    void onSaveSuccess();
}

public class HomeFrame extends JFrame implements ActionListener, SaveListener {
    JLabel label = new JLabel();

    // For navigation panel
    JPanel navigationPanel = new JPanel(null);
    ImageIcon addImageIcon = new ImageIcon("Frames\\pictures\\plus.png");
    JButton addButton = new JButton(addImageIcon);
    ImageIcon removeImageIcon = new ImageIcon("Frames\\pictures\\trash.png");
    JButton removeButton = new JButton(removeImageIcon);
    ImageIcon updateImageIcon = new ImageIcon("Frames\\pictures\\refresh.png");
    JButton updateButton = new JButton(updateImageIcon);
    JButton saveButton = new JButton("Save");
    JButton exitButton = new JButton("Continue");

    // For menu bar
    JPanel menuBar = new JPanel(null);
    ImageIcon searchImageIcon = new ImageIcon("Frames\\pictures\\search.png");
    JButton searchButton = new JButton(searchImageIcon);
    JTextField searchTextField = new JTextField();
    ImageIcon logoImageIcon = new ImageIcon();

    // For main panel
    JPanel mainPanel = new JPanel(null);
    private boolean modified = false;

    // Other code...

    // Method to set the modified flag
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    // Method to check if any changes have been made
    public boolean isModified() {
        return modified;
    }

    HomeFrame() {
        // Navigation panel settings
        navigationPanel.setBackground(new Color(253, 255, 194));
        //right side panel adjust
        navigationPanel.setBounds(850, 0, 240, 700);

        // Navigation panel components
        // Add button settings
       // addButton.setBounds(32, 170, 50, 50);
       // addButton.addActionListener(this);
        navigationPanel.add(addButton);

        // Remove button settings
        //removeButton.setBounds(32, 270, 50, 50);
        //removeButton.addActionListener(this);
        navigationPanel.add(removeButton);

        // Update button settings
        //updateButton.setBounds(32, 370, 50, 50);
        //updateButton.addActionListener(this);
        navigationPanel.add(updateButton);

        // Save button settings
        //saveButton.setBounds(5, 530, 105, 40);
        //saveButton.addActionListener(this);
        navigationPanel.add(saveButton);

        // Continue button settings
        //exitButton.setBounds(5, 580, 105, 40);
       // exitButton.addActionListener(this);
        navigationPanel.add(exitButton);

        //Panel sa taas settings
        menuBar.setBackground(new Color(148, 255, 216));
        menuBar.setBounds(0, 0, 970, 60);

        // Menu bar components
        // Search text field settings
        //searchTextField.setBounds(450, 10, 430, 40);
        menuBar.add(searchTextField);

        // Search button settings
        //searchButton.setBounds(900, 5, 50, 50);
        //searchButton.addActionListener(this);
        menuBar.add(searchButton);

        // Main panel settings
        mainPanel.setBackground(new Color(246, 246, 246));
        mainPanel.setBounds(0, 60, 970, 660);

        // Load movies from the watchlist file
       // loadMoviesFromWatchlist();

        // Frame settings
        add(label);
        setTitle("BubbleBlastGame");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        ImageIcon icon = new ImageIcon("Frames\\pictures\\photo_2024-03-30_23-39-13.jpg");
        setIconImage(icon.getImage());
        add(navigationPanel);
        add(menuBar);
        add(mainPanel);
    }

    @Override
    public void onSaveSuccess() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onSaveSuccess'");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
/* 
    @Override
    public void actionPerformed(ActionEvent e) {
        // For navigation buttons
        if (e.getSource() == addButton) {
            setModified(true);
            AddMovie add = new AddMovie();
            add.setVisible(true);
            add.setLocationRelativeTo(null);

            add.addWindowListener((WindowListener) new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    displayMovies();
                }
            });
        } 
        
        else if (e.getSource() == removeButton) {
            setModified(true);
            if (mainPanel.getComponentCount() == 0) {
                JOptionPane.showMessageDialog(null, "There are no movies to remove. Please add movies first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean anySelected = false;
            for (Component component : mainPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel moviePanel = (JPanel) component;
                    JCheckBox checkBox = (JCheckBox) moviePanel.getComponent(3);
                    if (checkBox.isSelected()) {
                        anySelected = true;
                        break;
                    }
                }
            }
            if (!anySelected) {
                JOptionPane.showMessageDialog(null, "Please select a movie to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the selected movies?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                Component[] components = mainPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JPanel) {
                        JPanel moviePanel = (JPanel) component;
                        JCheckBox checkBox = (JCheckBox) moviePanel.getComponent(3);
                        if (checkBox.isSelected()) {
                            mainPanel.remove(moviePanel);
                            String title = ((JLabel) moviePanel.getComponent(0)).getText().substring(7);
                            String genre = ((JLabel) moviePanel.getComponent(1)).getText().substring(7);
                            int releaseYear = Integer.parseInt(((JLabel) moviePanel.getComponent(2)).getText().substring(6));
                            MovieList.getInstance().removeMovie(title, genre, releaseYear);
                        }
                    }
                }
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        } 
     else if (e.getSource() == updateButton) {
        setModified(true);
        int selectedCount = 0; // Bilang ng mga naka-select na checkboxes
        Component selectedComponent = null; // Component na naka-select

        for (Component component : mainPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel moviePanel = (JPanel) component;
                JCheckBox checkBox = (JCheckBox) moviePanel.getComponent(3);
                if (checkBox.isSelected()) {
                    selectedCount++;
                    selectedComponent = component;
                    if (selectedCount > 1) {
                        JOptionPane.showMessageDialog(null, "Please select only one movie to update.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        }

        // Kapag walang naka-select na movie
        if (selectedCount == 0) {
            JOptionPane.showMessageDialog(null, "Please select a movie to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

      
        String selectedTitle = ((JLabel) ((JPanel) selectedComponent).getComponent(0)).getText().substring(7);
        String selectedGenre = ((JLabel) ((JPanel) selectedComponent).getComponent(1)).getText().substring(7);
        String selectedYear = ((JLabel) ((JPanel) selectedComponent).getComponent(2)).getText().substring(6);

        UpdateMovie update = new UpdateMovie(selectedTitle, selectedGenre, selectedYear);
        update.setVisible(true);
        update.setLocationRelativeTo(null);

        // Magdagdag ng WindowListener para ma-refresh ang display ng movie sa HomeFrame
        update.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                displayMovies(); // Refresh the display after updating
            }
        });
    } 
        
        else if (e.getSource() == saveButton) {
            if (!isModified()) {
                JOptionPane.showMessageDialog(null, "Nothing new to be saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                SaveButton saveButton = new SaveButton(this);
                saveButton.saveWatchlistToFile();
                setModified(false);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to save watchlist data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        
        else if (e.getSource() == exitButton) {
            ProgrammersProfileExit profileExit = new ProgrammersProfileExit();
            profileExit.setVisible(true);
            profileExit.setLocationRelativeTo(null);
            this.dispose();
        }
        // For menu bar button
        else if (e.getSource() == searchButton) {
            String searchText = searchTextField.getText().trim().toLowerCase();
            if (searchText.isEmpty()) {
                new SearchMovie(null, "Please enter a title, genre, or year to search.").setVisible(true);
                return;
            }

            // Initialize the search results list
            List<Movie> searchResults = new ArrayList<>();
            String searchTextLowerCase = searchText.toLowerCase();

            if (searchText.matches("\\d+")) {
                // If the search text contains only digits, search for movies by release year
            int releaseYear = Integer.parseInt(searchText);
                // Use the updated search method to get all movies with the specified release year
             searchResults.addAll(MovieList.getInstance().searchMovieByReleaseYear(releaseYear));

            } else {
                // Search for movies whose title or genre contains the entered text
                for (Movie movie : MovieList.getInstance().getMovies()) {
                     if (movie.getTitle().toLowerCase().contains(searchTextLowerCase) ||
                         movie.getGenre().toLowerCase().contains(searchTextLowerCase)) {
                     searchResults.add(movie);
        }
    }
}          
                interpolationSort(searchResults);
                // Display search results
                if (searchResults.isEmpty()) {
                     new SearchMovie(null, "No movies found matching the search criteria.").setVisible(true);
            } else {
                     new SearchMovie(searchResults, null).setVisible(true);
                    }
        }
    }
    //for title alphabetically order
    private void interpolationSort(List<Movie> searchResults) {
        int n = searchResults.size();
        for (int i = 1; i < n; ++i) {
            Movie key = searchResults.get(i);
            int j = i - 1;
        
            while (j >= 0 && searchResults.get(j).getTitle().compareToIgnoreCase(key.getTitle()) > 0) {
                searchResults.set(j + 1, searchResults.get(j));
                j = j - 1;
            }
            searchResults.set(j + 1, key);
        }
    }
    
    private void displayMovies() {
        mainPanel.removeAll();
    
        List<Movie> searchResults = MovieList.getInstance().getMovies();
    
        interpolationSort(searchResults);
    
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 33, 40));
    
        int count = 0;
    
        for (Movie movie : searchResults) {
            if (count >= 12) {
                JOptionPane.showMessageDialog(null, "We apologize, but the program cannot handle more than 12 movies at once. Please remove a movie to add another one.", "Limit Exceeded", JOptionPane.ERROR_MESSAGE);
                break;
            }
    
            JPanel moviePanel = new JPanel(new GridLayout(4, 1));
            moviePanel.setBackground(new Color(255, 199, 199));
            moviePanel.setBorder(BorderFactory.createLineBorder(Color.black));
    
            JLabel titleLabel = new JLabel("Title: " + movie.getTitle());
            JLabel genreLabel = new JLabel("Genre: " + movie.getGenre());
            JLabel yearLabel = new JLabel("Year: " + movie.getReleaseYear());
    
            JCheckBox checkBox = new JCheckBox();
    
            moviePanel.setPreferredSize(new Dimension(200, 150));
    
            moviePanel.add(titleLabel);
            moviePanel.add(genreLabel);
            moviePanel.add(yearLabel);
            moviePanel.add(checkBox);
    
            mainPanel.add(moviePanel);
            count++;
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void loadMoviesFromWatchlist() {
        try (BufferedReader reader = new BufferedReader(new FileReader("watchlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3) {
                    String title = parts[0];
                    String genre = parts[1];
                    int releaseYear = Integer.parseInt(parts[2]);
                    Movie movie = new Movie(title, genre, releaseYear);
                    MovieList.getInstance().addMovie(movie);
                }
            }
            displayMovies();
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSaveSuccess() {
        displayMovies();
        JOptionPane.showMessageDialog(this, "Watchlist data saved successfully.", "Saved", JOptionPane.INFORMATION_MESSAGE);
    }
}*/