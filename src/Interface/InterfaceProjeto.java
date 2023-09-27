package Interface;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Hash.tabelaHash;

public class InterfaceProjeto extends JFrame implements ActionListener {
    // Componente de texto
    JTextPane t;

    // Frame
    JFrame f;

    // table hash
    private tabelaHash dicionario;
    // Scroll
    JScrollPane scrollPane;

    private void removerEstiloNegrito() {
        StyledDocument doc = t.getStyledDocument();
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
    }

    // Construtor
    public InterfaceProjeto()
    {
        // Cria um Frame
        f = new JFrame("editor");

        try {
            // Defina a aparência e o toque do metal
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // Set a o tema
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch (Exception e) {
        }

        // Componente de texto
        t = new JTextPane();
        StyledDocument doc = t.getStyledDocument();


        // instancia um JMenuBar
        JMenuBar mb = new JMenuBar();

        // cria um novo JMenu Arquivo
        JMenu m1 = new JMenu("Arquivo");

        // Cria itens para o Arquivo
        JMenuItem mi1 = new JMenuItem("Novo");
        JMenuItem mi2 = new JMenuItem("Abrir");
        JMenuItem mi3 = new JMenuItem("Salvar");
        JMenuItem mi9 = new JMenuItem("Imprimir");
        JMenuItem mi10 = new JMenuItem("Salvar como..");

        // Adiciona ações para os itens de arquivo
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);
        mi10.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);
        m1.add(mi10);

        // Cria um novo JMneu Editar
        JMenu m2 = new JMenu("Editar");

        // Cria itens para o Editar
        JMenuItem mi4 = new JMenuItem("Cortar");
        JMenuItem mi5 = new JMenuItem("Copiar");
        JMenuItem mi6 = new JMenuItem("Colar");

        // Adicina ações para os itens de Editar
        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);

        JMenuItem mc = new JMenuItem("Fechar");
        mc.setPreferredSize(new Dimension(60, 20));

        mc.addActionListener(this);

        mb.add(m1);
        mb.add(m2);
        mb.add(mc);

        // Cria um JMenu Dicionário
        JMenu dic = new JMenu("Dicionário");

        // Cria itens para o Dicionario
        JMenuItem mi7 = new JMenuItem("Verificar");
        JMenuItem mi8 = new JMenuItem("Adicionar");

        // Adiciona ações para os itens de Dicionário
        mi7.addActionListener(this);
        mi8.addActionListener(this);

        dic.add(mi7);
        dic.add(mi8);

        mb.add(dic);

        // Crie um novo JScrollPane
        scrollPane = new JScrollPane(t);


        f.setJMenuBar(mb);
        f.add(scrollPane);
        t.setEditable(true);  // Definir como não editável
        f.setSize(500, 500);
        // Defina a visibilidade do JFrame como true
        f.setVisible(true);

        f.show();

        //Criando DIcionario
        dicionario = new tabelaHash();
    }

    private void carregarArquivo(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            DefaultStyledDocument doc = new DefaultStyledDocument(); // Utiliza DefaultStyledDocument
            t.setStyledDocument(doc); // Defina o novo documento estilizado

            String line;
            while ((line = br.readLine()) != null) {
                String[] palavras = line.split("\\s+");
                for (String palavra : palavras) {
                    if (dicionario.buscar(palavra)) {
                        doc.insertString(doc.getLength(), palavra + " ", null); // Insere palavra normalmente
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(f, "Erro ao carregar o arquivo: " + e.getMessage());
        } catch (BadLocationException e) {
            JOptionPane.showMessageDialog(f, "Erro ao aplicar estilo ao texto: " + e.getMessage());
        }
    }



    private void aplicarEstiloNegrito(String texto) {
        StyledDocument doc = t.getStyledDocument();
        Style style = doc.addStyle("NotFoundStyle", null);
        StyleConstants.setBold(style, true);

        String[] palavras = texto.split("\\s+");
        for (String palavra : palavras) {
            if (!dicionario.buscar(palavra)) {
                int startIndex = texto.indexOf(palavra);
                int endIndex = startIndex + palavra.length();
                doc.setCharacterAttributes(startIndex, endIndex - startIndex, style, true);
            }
        }
    }



    private void adicionarPalavrasDoEditor() {
        String texto = t.getText();
        String[] palavras = texto.split("\\s+");
        for (String palavra : palavras) {
            if (!dicionario.buscar(palavra)) {
                dicionario.adicionar(palavra);
            }
        }

        removerEstiloNegrito();

        // Salvar as palavras adicionadas em um arquivo
        FileWriter writer;
        try {
            writer = new FileWriter("dicionario.txt");
            for (String palavra : dicionario.getPalavras()) {
                writer.write(palavra + "\n");
            }
            writer.close();
            JOptionPane.showMessageDialog(f, "Palavras adicionadas ao dicionário com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(f, "Erro ao escrever no arquivo: " + e.getMessage());
        }
    }


    private boolean verificarPalavraNoDicionario(String palavra) {
        try (BufferedReader br = new BufferedReader(new FileReader("dicionario.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals(palavra)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(f, "Erro ao ler o arquivo: " + e.getMessage());
        }
        return false;
    }


    private void adicionarTextoComEstilo(JTextPane textPane, String texto) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("bold" , null);
        StyleConstants.setBold(style, true);

        String[] palavras = texto.split("\\s+");
        for (String palavra : palavras) {
            if (verificarPalavraNoDicionario(palavra)) {
                adicionarTexto(doc, palavra + " ", null); // Adiciona texto normal
            } else {
                adicionarTexto(doc, palavra + " ", style); // Adiciona texto em negrito
            }
        }
    }

    private void adicionarTexto(StyledDocument doc, String texto, Style style) {
        try {
            if (style == null) {
                doc.insertString(doc.getLength(), texto, null);
            } else {
                doc.insertString(doc.getLength(), texto, style);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


    // Se um botão é pressionado

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        if (s.equals("Cortar")) {
            t.cut();
        }
        else if (s.equals("Copiar")) {
            t.copy();
        }
        else if (s.equals("Colar")) {
            t.paste();
        }
        else if (s.equals("Verificar")) {
            String texto = t.getText();
            String[] palavras = texto.split("\\s+");
            StringBuilder resultado = new StringBuilder();

            for (String palavra : palavras) {
                if (verificarPalavraNoDicionario(palavra)) {
                    resultado.append(palavra).append(": Encontrada\n");
                } else {
                    resultado.append(palavra).append(": Não encontrada\n");
                    aplicarEstiloNegrito(palavra);
                }
            }

            // Barra de rolagem


            JOptionPane.showMessageDialog(f, resultado.toString());
        }


        else if (s.equals("Adicionar")) {
            adicionarPalavrasDoEditor();
        }
        else if (s.equals("Salvar")) {
            // Cria um objeto da classe JFileChooser
            JFileChooser j = new JFileChooser("f:");

            // Invoca a função showsSaveDialog para mostrar a caixa de diálogo de salvamento
            int r = j.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {

                // Defina a label para o caminho do diretório selecionado
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    // Cria um arquivo de escrita
                    FileWriter wr = new FileWriter(fi, false);

                    // Cria um BufferedWriter para escrits
                    BufferedWriter w = new BufferedWriter(wr);

                    // Write
                    w.write(t.getText());

                    w.flush();
                    w.close();
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            // Se o usuário cancelar a operação
            else
                JOptionPane.showMessageDialog(f, "O usuário cacnelou a operação");
        }
        else if (s.equals("Imprimir")) {
            try {
                // Imprime o arquivo
                t.print();
            }
            catch (Exception evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        }
        else if (s.equals("Abrir")) {
            // Cria um objeto da classe JFileChooser
            JFileChooser j = new JFileChooser("f:");

            // Invoca a função showsOpenDialog para mostrar a caixa de diálogo de salvamento
            int r = j.showOpenDialog(null);



            // Se o usuário selecionar um arquivo
            if (r == JFileChooser.APPROVE_OPTION) {
                // Defina uma label para o caminho do diretório selecionado
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                File file = j.getSelectedFile();
                carregarArquivo(file);



                try {
                    // String
                    String s1 = "", sl = "";

                    // Ler arquivo
                    FileReader fr = new FileReader(fi);

                    // leitura com BufferedReader
                    BufferedReader br = new BufferedReader(fr);

                    // Inicializa s1
                    sl = br.readLine();

                    // pega o input do arquivo
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }

                    // Seta o texto
                    t.setText("");
                    adicionarTextoComEstilo(t, sl);
                    br.close();
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            // Se o usuário cancelou a operação
            else
                JOptionPane.showMessageDialog(f, "O usuário cancelou a operação");
        }
        else if (s.equals("Novo")) {
            t.setText("");
        }
        else if (s.equals("Fechar")) {
            f.setVisible(false);
        }

        else if (s.equals("Salvar como..")) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                saveFileAs(selectedFile);
            }
        }
    }

    private void saveFileAs(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(t.getText());
            writer.flush();
            writer.close();
            JOptionPane.showMessageDialog(f, "Arquivo salvo com sucesso!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, "Error while saving the Arquivo.");
        }
    }
}