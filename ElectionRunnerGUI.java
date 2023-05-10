package OOP.ec22707.A7;

import OOP.ec22707.A7.contributions.*;

import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class ElectionRunnerGUI {
    private JPanel rootPanel;
    private JLabel Title;
    private JButton runElectionOne;
    private JTextArea electionResult;
    private JLabel noCandidates;
    private JLabel noTimes;
    private JTextField noTimesInput;
    private JTextField noCandidatesInput;
    private JButton runElectionTwo;
    private JTextField seedInput;
    private JLabel seed;

    public ElectionRunnerGUI() {
        Candidate[] possibleCandidates = A3.getCandidateArray();
        runElectionOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                electionResult.setText("");
                if (noCandidatesInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the number of candidates", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (noTimesInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the number of times", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (seedInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the seed", "Error", JOptionPane.ERROR_MESSAGE);
                }
                int n = Integer.parseInt(noCandidatesInput.getText().trim());
                int times = Integer.parseInt(noTimesInput.getText().trim());
                Candidate[] candidates = randomCandidates(n, possibleCandidates);
                for (int i = 0; i < times; i++) {

                    Candidate w = (new Candidate_eey577()).selectWinner(
                            getVotes(candidates, candidates));
                    electionResult.append("The winner is " + w.getName() + " (" + w.un + "): " + w.getSlogan() + "\n");
                }
            }
        });
        runElectionTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                electionResult.setText("");
                if (noCandidatesInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the number of candidates", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (noTimesInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the number of times", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (seedInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the seed", "Error", JOptionPane.ERROR_MESSAGE);
                }
                final Citizen trustedCounter = new Candidate_eey577();

                int number = Integer.parseInt(noCandidatesInput.getText().trim());
                Candidate[] candidates = randomCandidates(number, possibleCandidates);

                Candidate[] votes = getVotes(candidates, candidates);

                Candidate w1 = trustedCounter.selectWinner(votes);
                electionResult.append("\nThe 1-winner is " + w1.getName() + " (" + w1.un + "): " + w1.getSlogan()+"\n");

                electionResult.append("\nAccording to the voters, the winners are \n");
                Candidate[] winners = getWinners(votes, candidates);

                Candidate w2v = trustedCounter.selectWinner(winners);
                electionResult.append("\nThe 2-winner (voters counting) is " +
                        w2v.getName() + " (" + w2v.un + "): " +
                        w2v.getSlogan()+"\n");

                electionResult.append("\nAccording to the candidates, the winners are \n");
                winners = getWinners(votes, candidates);

                Candidate w2c = trustedCounter.selectWinner(winners);
                electionResult.append("\nThe 2-winner (candidates counting) is " +
                        w2c.getName() + " (" + w2c.un + "): " +
                        w2c.getSlogan()+"\n");

            }
        });
    }

    private static Candidate[] getVotes(Candidate[] candidates, Citizen[] va) {

        Candidate[] temp = new Candidate[va.length];
        int count = 0;
        for (int i = 0; i < va.length; i++) {
            if (proper(va[i])) {
                try {
                    temp[count] = va[i].vote(candidates);
                    //System.err.print(" "+temp[count].getName());
                    count++; // Won't happen if vote throws an exception.
                } catch (Exception e) { // 'Spoilt ballot'
                    if (showErrors) e.printStackTrace();
                }
            }
        }

        Candidate[] votes = new Candidate[count];
        for (int i = 0; i < count; i++) votes[i] = temp[i];
        return votes;
    }

    private static Candidate[] getWinners(Candidate[] candidates, Citizen[] va) {

        Candidate[] temp = new Candidate[va.length];
        int count = 0;
        for (int i = 0; i < va.length; i++) {
            if (proper(va[i])) {
                try {
                    temp[count] = va[i].selectWinner(candidates);
                    System.err.print(" " + temp[count].getName());
                    count++; // Won't happen if selectWinner throws an exception.
                } catch (Exception e) { // 'Spoilt count'
                    if (showErrors) e.printStackTrace();
                }
            }
        }

        Candidate[] winners = new Candidate[count];
        for (int i = 0; i < count; i++) winners[i] = temp[i];
        return winners;
    }

    private Candidate[] randomCandidates(int number, Candidate[] ca) {

        int seed = Integer.parseInt(seedInput.getText().trim());

        Candidate[] candidates = new Candidate[number];

        Random rs = (seed == 0 ? new Random() : new Random(seed));
        electionResult.append("The candidates are \n");
        for (int i = 0; i < number; i++) {
            do {
                candidates[i] = ca[rs.nextInt(ca.length)];
            }
            while (!proper(candidates[i]));

            electionResult.append(candidates[i].getName() + "\n");
        }
        return candidates;
    }


    private static boolean proper(Person p) {
        return !(p.getName().length() > 6 &&
                p.getName().substring(0, 7).equals("No name"));
    }

    private static boolean showErrors = false;


    public static void main(String[] args) {
        JFrame frame = new JFrame("ElectionRunnerGUI");
        frame.setContentPane(new ElectionRunnerGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
