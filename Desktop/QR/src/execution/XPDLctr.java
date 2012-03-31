
package execution;

import java.io.*;
import java.util.Date;
import org.jdom.*;
import org.jdom.output.*;
/**
 *
 * @author Kubik
 */
public class XPDLctr {
    
  // namespace
 private static Element xpdlElement = null;

 private static Document document;
 
 //niveau d'imbrication
 private static int level = 5;
  
private static void createXpdlDocument(Element xpdl){
    //the cutials elements of the header
    String xmlns_xpdl = "http://www.wfmc.org/2002/XPDL1.0";
    String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";
    String xmlns_location = "xsi:schemaLocation=\"http://www.wfmc.org/2002/XPDL1.0 ";
    String xsi_schemaLocation = "http://www.wfmc.org/2002/XPDL1.0 http://www.wfmc.org/standards/docs/xpdl.xsd";
    
    String idPackage = "xpdl-test";//to be defined 
    String namePackage = "xpdl-test";//to be defined
    
    /* To concatenate the elements of XPDL file header:
     * getNamespace(String, variable); 
     * addNamespaceDeclaration(variable);
     * result: 
     * <Package xmlns:xpdl="http://www.wfmc.... xmlns:xsi="http://www.wfmc.org/2002/... 
     */
    Namespace xpdlns = Namespace.getNamespace("xpdl", xmlns_xpdl); 
    xpdl.addNamespaceDeclaration(xpdlns);
    Namespace xpdlns2 = Namespace.getNamespace("xsi", xmlns_xpdl);
    xpdl.addNamespaceDeclaration(xpdlns2);
    Namespace xpdlns3 = Namespace.getNamespace("location", xmlns_location);
    xpdl.addNamespaceDeclaration(xpdlns3);
    Namespace xpdlns4 = Namespace.getNamespace("schemaLocation", xsi_schemaLocation);
    xpdl.addNamespaceDeclaration(xpdlns4);
    
    //two elements that won't begin with "xmlns:"
    xpdl.setAttribute("Id", idPackage);
    xpdl.setAttribute("Name", namePackage);
    createHeader();
    createWorkflow();
}
/* Create optional element of the header */
private static void createHeader(){
    Element packageHeader = new Element("PackageHeader");
    xpdlElement.addContent(packageHeader);
    
    /*insert new tags between the root tags
     * <PackageHeader>
     * <XPDLVersion>1.0</XPDLVersion>
    */
    Element xpdlVersion = new Element("XPDLVersion");
    packageHeader.addContent(xpdlVersion);
    //write string between tags
    xpdlVersion.setText("1.0");
    
    Element vendor = new Element("Vendor");
    packageHeader.addContent(vendor);
    vendor.setText("Kamil and Florent");
    
    Element createDate = new Element("Created");
    packageHeader.addContent(createDate);
    Date date = new Date();
    createDate.setText(date.toString()); 
}

private static void createWorkflow(){
// build header of workflow processes
 Element workflowProcesses = new Element("WorkflowProcesses");
 xpdlElement.addContent(workflowProcesses);
 Element workflowProcess = new Element("WorkflowProcess");
 workflowProcesses.addContent(workflowProcess);
 
 workflowProcess.setAttribute("Id", "1");//a definir les entrees idee: variable statique++
 workflowProcess.setAttribute("Name", "1");//a definir les entrees idee: variable statique++
 
 Element processHeader = new Element("ProcessHeader");
 workflowProcess.addContent(processHeader);
    /*
     * IL NOUS RESTE A DEFINIR COMBIEN DES CHAMPS 
     * IL FAUT PRENDRE EN COMPTE ILYA FORMALPARAMETERS
     * DATAFIELDS APPLICATION
     */
 // create activitys and transitions
    addActivities(workflowProcess);   
    addTransitions(workflowProcess);
}

private static void addActivities(Element workflowProcess){
    Element activities = new Element("Activities");
    workflowProcess.addContent(activities);
   
for (int i = 1; i <= level ; i++) {
         String id = Integer.toString(i);
         Element activity = addActivity(id);
         activities.addContent(activity);
         if (i == level) {
           break;  
         }
}

}
private static Element addActivity(String id){
    
Element activity = new Element("Activity");

 activity.setAttribute("Id", id);
 activity.setAttribute("Name", id);
 Element implementation = new Element("Implementation");
 implementation.addContent(new Element("No"));
 activity.addContent(implementation);
 Element transitionRestrictions = addTransitionRestrictions();
return activity;
}
/*A IPLEMENTER ********************************************************/
private static Element addTransitionRestrictions(){
Element restrictions = new Element("TransitionRestrictions");
return restrictions;
}
/*A IPLEMENTER ********************************************************/
private static Element addTransitions(Element workflowProcess){
Element transition = new Element("Transitions");
return transition;
}

static void showDocument()
{
try
{
//On utilise ici un affichage classique avec getPrettyFormat()
XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
sortie.output(document, System.out);

}
catch (java.io.IOException e){}
}

/* MAIN */

public static void main(String[] args)
{
 
 xpdlElement = new Element("Package");
  //definition de la racine du document
 document = new Document(xpdlElement);
 
createXpdlDocument(xpdlElement);
  
showDocument(); //définie plus loin
}

}