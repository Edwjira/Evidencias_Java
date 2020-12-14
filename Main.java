import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.beans.*;
import java.util.Random;

// Creamos el objeto Dado, que tendra como parametro el resultado de lanzarlo. Contendra dos metodos: lanzarDado y valor. El metodo de lanzarDado obtiene un valor generado aleatoriamente entre 1 y 6, y se lo asigna al valor del objeto dado. El metodo valor simplemente nos retorna el valor que tiene el dado. 
class Dado {
    private Integer resultado;
    public void lanzarDado() {
        Random ran = new Random();
        resultado = ran.nextInt(6) + 1;
    }
    public Integer valor() {
        return resultado;
    }
}

// La clase Juego es la que controla nuestro juego. 
class Juego {
    public Boolean inicio = Boolean.TRUE; // Nos indica si tenemos que dar la primera tirada. 
    public Integer sum; // Guarda la suma de los dados despues de obtener el punto. 
    public Integer punto; // Guarda el valor del punto obtenido. 
    public void iniciar() {
            Dado dado1 = new Dado(); // Creamos el dado 1. 
            Dado dado2 = new Dado(); // Creamos el dado 2. 
            JFrame f = new JFrame(); // Creamos la ventana principal. 
            int origen_x = 300; // Establecemos la coordenada central en x de la ventana. 
            int origen_y = 300; // Establecemos la coordenada central en y de la ventana. 
            int dimension_boton = 100; // Establecemos un tamano para el boton de lanzar. 
            JLabel d1 = new JLabel(), d2 = new JLabel(); // Creamos dos etiquetas que nos mostraran las imagenes que correspondientes a los dados creados. 
            d1.setIcon(new ImageIcon("1.png")); // Colocamos una cara por defecto en el dado 1. 
            d1.setBounds(80, 50, 200, 200); // Establecemos las coordenadas donde colocaremos el dado y establecemos tambien sus dimensiones. 
            d2.setIcon(new ImageIcon("1.png")); // Colocamos una cara por defecto en el dado 2. 
            d2.setBounds(320, 50, 200, 200); // Establecemos las coordenadas donde colocaremos el dado y establecemos tambien sus dimensiones.
            f.add(d1); // Agregamos a la ventana el dado 1.
            f.add(d2); // Agregamos a la ventana el dado 2. 

            // Creamos un Listener que nos detectara cuando le damos click al objeto que le asignemos el Listener, en este caso a nuestro boton de lanzar. 

            ActionListener procesar_clicks = new ActionListener() {
                    public void actionPerformed(ActionEvent e) { // Procesar accion 
                        dado1.lanzarDado(); // Lanzamos el dado 1. 
                        dado2.lanzarDado(); // Lanzamos el dado 2. 
                        d1.setIcon(new ImageIcon(dado1.valor() + ".png")); // Cambiamos la imagen del dado 1 al valor obtenido de girar el dado.
                        d2.setIcon(new ImageIcon(dado2.valor() + ".png")); // Cambiamos la imagen del dado 2 al valor obtenido de girar el dado.
                        sum = dado1.valor() + dado2.valor();
                        if (inicio) { // Si es la tirada inicial 
                            inicio = Boolean.FALSE; // Decimos que ya hicimos la tirada 
                            if (sum == 2 || sum == 3 || sum == 12) { // Para la tirada inicial, sacar un 2, 3 o 12 pronostica una derrota. 

                                JOptionPane.showMessageDialog(null, "Perdiste", "PERDEDOR", JOptionPane.INFORMATION_MESSAGE); // Mostramos una ventana emergente que indica que perdimos. 
                                System.exit(0); // Cerramos todo el programa. 
                            } else if (sum == 7 || sum == 11) {
                                JOptionPane.showMessageDialog(null, "Ganaste", "GANADOR", JOptionPane.INFORMATION_MESSAGE); // Mostramos una ventana emergente que indica que ganamos. 
                                System.exit(0); // Cerramos todo el programa. 
                            } else { // Para la tirada inicial, obtener cualquier resultado que no se haya mencionado arriba indica el estado de punto. 
                                JLabel txt = new JLabel();
                                punto = sum; // Decimos entonces que el valor del punto es la suma de los dados obtenida en la primera tirada. 

                                JOptionPane.showMessageDialog(null, "Sacaste Punto, continua lanzando", "AGAIN", JOptionPane.INFORMATION_MESSAGE); // Mostramos una ventana emergente que indica que obtuvimos punto, y que podemos continuar tirando. 
                                txt.setText("Punto = " + punto); // Fijamos el texto de nuestra etiqueta para que nos indique el valor que tenemos que lanzar de nuevo para ganar. 
                                txt.setFont(new Font("Courier", Font.BOLD, 40)); // Fijamos la fuente y tamano del texto, e indicamos que se ponga en negrilla. 
                                txt.setBounds(175, 300, 250, 80);
                                f.add(txt); // Agregamos esta etiqueta a la ventana. 
                            }
                        } else { // Si estamos despues de la tirada inicial y en el estado de punto: 
                            if (sum == punto) {
                                JOptionPane.showMessageDialog(null, "Ganaste", "GANADOR", JOptionPane.INFORMATION_MESSAGE); // Mostramos una ventana emergente que indica que ganamos. 
                                System.exit(0); // Cerramos todo el programa. 
                            } else if (sum == 7) { // Sacar un 7 en el estado de punto indica una derrota. 
                                JOptionPane.showMessageDialog(null, "Perdiste", "PERDEDOR", JOptionPane.INFORMATION_MESSAGE); //Mostramos una ventana emergente que indica que perdimos. 
                                System.exit(0); // Cerramos todo el programa. 
                            } else {
                                JOptionPane.showMessageDialog(null, "Continua lanzando", "AGAIN", JOptionPane.INFORMATION_MESSAGE); // Mostramos una ventana emergente que indica que podemos continuar tirando. 
                                } 
                                } 
                              f.repaint(); // Actualizamos la ventana, pues si no hacemos eso las imagenes que cambiamos no se veran
                              f.revalidate(); // Verificamos que la actualizacion se haya ejecutado, de lo contrario, forzamos a que la aventana se refresque. 
                    }
                            };
            
                            JButton b = new JButton(); // Creamos el boton de lanzar. 
                            b.setText("Lanzar"); // Fijamos el texto de este boton a lanzar. 

                            b.setBounds(origen_x - dimension_boton / 2, 2 * origen_y - (3 / 2) * dimension_boton, dimension_boton, dimension_boton / 2); // Fijamos las coordenadas donde se colocara este boton y sus dimensiones. 
                            b.addActionListener(procesar_clicks); // Le agregamos al boton un Listener con el que sabra cuando le damos 
                            f.add(b); // Agregamos el boton a la ventana. 
                            f.setSize(600, 600); // Fijamos el tamano de la ventana. 
                            f.setLayout(null); // Dictamos que la ventana no tenga un layout, por lo que estaremos usando coordenadas para ubicar Los elementos graficos. 
                            f.setVisible(true); // Dictamos que la ventana sea visible, de lo contrario algunas veces la ventana no la podremos ver. 
                            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Establecemos que al dar click en el icono de cerrar ventana, efectivamente el programa se cierre. 
            }
}

public class Main {
  public static void main(String[] args) {
      Juego craps = new Juego(); // Creamos un objeto de Juego. 
      craps.iniciar(); // Iniciamos el juego. 
  }
}