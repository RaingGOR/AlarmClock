
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import static java.lang.Integer.parseInt;

public class MainFrame extends JFrame {
    /**
     * Launch the application.
     * Program show real time and can set alarm.
     * Written program with Java 17 and library SWING
     *
     * @author Raingor(Bulat Kadirov)
     */
    private JPanel contentPane;
    private GregorianCalendar responseTime;
    static int i = 0, in = 365;

    private boolean buffer = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //set formatDate
    private String getStringNowDate(String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        return formatDate.format(new GregorianCalendar().getTime());
    }

    //play sound
    public void playSoundFromResourse(String filePath) {
        String fullFilePath = "resourse\\" + filePath;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File((fullFilePath)));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public MainFrame() throws IOException, FontFormatException {
        setTitle("Alarm Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 225, dimension.height / 2 - 150, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.BLACK);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);

        /*
         *This first dev for show real time.
         */
        JLabel labelTime = new JLabel();
        Font font = new Font("Times New Roman", Font.PLAIN, 60);
        labelTime.setText("--:--:--");
        labelTime.setFont(font);
        labelTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTime.setForeground(Color.WHITE);
        /*
        Real time set
         */
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelTime.setText(getStringNowDate("HH:mm:ss"));
                if (responseTime == null) return;
                if (responseTime.before(new GregorianCalendar())) {
                    responseTime = null;
                    playSoundFromResourse("ding.wav");
                    if (buffer)
                        JOptionPane.showMessageDialog(MainFrame.this, "Alarm", "A.L.A.R.M.", JOptionPane.INFORMATION_MESSAGE);


                }}
            });
        timer.start();

            //add bottPanel
            JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(new

            TitledBorder("Set Alarm"));
        bottomPanel.setVisible(true);

            Font font2 = new Font("Verdana", Font.PLAIN, 10);

            JTextField alarmHoursField = new JTextField();
        alarmHoursField.setBackground(Color.GRAY);
        alarmHoursField.setBorder(new

            EmptyBorder(10,50,10,50));
        alarmHoursField.setForeground(Color.YELLOW);

            JTextField alarmMinutsField = new JTextField();
        alarmMinutsField.setForeground(Color.YELLOW);
        alarmMinutsField.setBorder(new

            EmptyBorder(10,50,10,50));
        alarmMinutsField.setBackground(Color.GRAY);


            JLabel information = new JLabel("Write Hours and Minutes");
        information.setForeground(Color.YELLOW);
        information.setBackground(Color.BLACK);

            JButton set = new JButton("SET");
        set.setForeground(Color.GREEN);
        set.setBackground(Color.BLACK);

            JLabel text = new JLabel();
        text.setBackground(Color.BLACK);
        text.setForeground(Color.YELLOW);
        text.setVisible(false);

        set.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    responseTime = new GregorianCalendar();
                    //alarm
                    if (alarmHoursField.getText().equals("") && alarmMinutsField.getText().equals("")) {
                        responseTime.set(Calendar.HOUR_OF_DAY, 0);
                        responseTime.set(Calendar.MINUTE, 0);
                        responseTime.set(Calendar.SECOND, 0);
                        String tex = "You need are set time! ";
                        text.setText(tex);
                        text.setVisible(true);

                    } else {
                        if (alarmHoursField.getText().equals("")) {
                            alarmHoursField.setText("0");
                        }
                        responseTime.set(Calendar.HOUR_OF_DAY, parseInt(alarmHoursField.getText()));
                        responseTime.set(Calendar.MINUTE, parseInt(alarmMinutsField.getText()));
                        responseTime.set(Calendar.SECOND, 0);

                        if (responseTime.before(new GregorianCalendar())) {
                            responseTime.add(Calendar.DAY_OF_MONTH, 1);
                        }

                        String tex = "You have successfully set the alarm for " + alarmHoursField.getText() + " : " + alarmMinutsField.getText() + " for 365 days";
                        text.setText(tex);
                        text.setVisible(true);
                        buffer = true;
                    }
                }
            });

        bottomPanel.add(information);
        bottomPanel.add(alarmHoursField);
        bottomPanel.add(alarmMinutsField);
        bottomPanel.add(set);
        bottomPanel.add(text);

        contentPane.add(labelTime);
        contentPane.add(bottomPanel);
        }
    }

