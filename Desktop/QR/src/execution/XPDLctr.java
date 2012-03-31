/*
 * To change this template, choose Tools | Templates
 * andimport java.io.*;
import org.jdom.*;
import org.jdom.output.*; open the template in the editor.
 */
package execution;

import java.io.*;
import org.jdom.*;
import org.jdom.output.*;

/**
 *
 * @author Kubik
 */
public class XPDLctr {
    
// create package element
private final static String xmlns_xpdl = "http://www.wfmc.org/2002/XPDL1.0";
private final static String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";
private final static String xmlns_location = "xsi:schemaLocation=\"http://www.wfmc.org/2002/XPDL1.0 ";
private final static String xsi_schemaLocation = "http://www.wfmc.org/2002/XPDL1.0 http://www.wfmc.org/standards/docs/xpdl.xsd";
private final static String packageId = "xpdl";

private Namespace ns;

static Namespace xpdlns = Namespace.getNamespace("xpdl", xmlns_xpdl); //public final

static Element racine = new Element("Package", xpdlns);
static org.jdom.Document document = new Document(racine);


public static void main(String[] args)
{

//On crée un nouvel Element etudiant et on l'ajoute en tant qu'Element de racine
Element etudiant = new Element("etudiant");
racine.addContent(etudiant);

//On crée un nouvel Attribut classe et on l'ajoute à etudiantgrâce à la méthode setAttribute
Attribute classe = new Attribute("classe","P2");
etudiant.setAttribute(classe);

//On crée un nouvel Element nom, on lui assigne du texte et on l'ajoute en tant qu'Element de etudiant
Element nom = new Element("nom");
nom.setText("CynO");
etudiant.addContent(nom);
affiche(); //définie plus loin
enregistre("Exercice1.xml"); //définie plus loin

}
static void affiche()
{
try
{
//On utilise ici un affichage classique avec getPrettyFormat()
XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
sortie.output(document, System.out);

}
catch (java.io.IOException e){}
}
static void enregistre(String fichier)
{
try
{
//On utilise ici un affichage classique avec getPrettyFormat()
XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
//Remarquez qu'il suffit simplement de créer une instance de FileOutputS avec en argument le nom du fichier pour effectuer la sérialisation.
sortie.output(document, new FileOutputStream(fichier));
}
catch (java.io.IOException e){}
}
}
