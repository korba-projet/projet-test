
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
 private static String joinSpecification = "A DEFINIR";
 
private static void createXpdlDocument(Element xpdl)  throws JDOMException {
    //the crutials elements of the header
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

private static void createWorkflow()  throws JDOMException {
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
 // create activities and transitions
    addActivities(workflowProcess);   
    addTransitions(workflowProcess);
}

private static void addActivities(Element workflowProcess){
    Element activities = new Element("Activities");
    workflowProcess.addContent(activities);
   
for (int i = 1; i <= level ; i++) {
    // char tmpAsci = (char)('A' + i - 1); - pour gerer les caracteres
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

/*********************************************/
 activity.setAttribute("Id", id); //IDEE A B C D
 activity.setAttribute("Name", id); //IDEE A B C D
 Element implementation = new Element("Implementation");
 implementation.addContent(new Element("No"));
 activity.addContent(implementation);
 
 
  Element startmode = new Element("StartMode");
  startmode.addContent(new Element("Automatic"));
  activity.addContent(startmode);
  Element finishmode = new Element("FinishMode");
  finishmode.addContent(new Element("Automatic"));
  activity.addContent(finishmode);
 
  /*add tag restrictions*/
  Element transitionRestrictions = addTransitionRestrictions();
  activity.addContent(transitionRestrictions);
  
return activity;
}

private static Element addTransitionRestrictions(){
 Element restrictions = new Element("TransitionRestrictions");
 Element interRestrictions = new Element("TransitionRestriction");
  // Join Type
  Element join = new Element("Join");
  join.setAttribute("Type", joinSpecification);
  interRestrictions.addContent(join);
  // Split Type = "AND"
  Element split = new Element("Split");
  split.setAttribute("Type", joinSpecification);
  interRestrictions.addContent(split);
  restrictions.addContent(interRestrictions);
  
return restrictions;
}


private static void addTransitions(Element workflowProcess)  throws JDOMException {
    
Element transition = new Element("Transitions");
  String toId1 = null;
  String toId2 = null;
  int tmp=0;
  for (int i = 1; i <= level ; i++) {
      for (int j = 1; j <= i; j++) { 
    if (((i+j) > (level)) || (i == level)) {
        break;
    }
    String fromId = Integer.toString(i) + Integer.toString(j);
    if (( i <= level/2 ) && ((i + j + 1) < (level  + 1))) { 
        tmp = i + 1 + j;
        toId2 = Integer.toString(i+1) + Integer.toString(j+1);
        if ((i + j + 1) < (level  + 1)) {
        tmp = i + 1 + j;
        toId1 = Integer.toString(i+1) + Integer.toString(j);
        }
    } else {
        if ((i + j) < (level  + 1)) {
        
        toId2 = Integer.toString(i+1) + Integer.toString(j-1);
        }
        if ((j -1) > 0) {
        toId2 = Integer.toString(tmp);
        
        }  
    }
    addTransition(transition, fromId + "To" + toId1, fromId, toId1);
    addTransition(transition, fromId + "To" + toId2, fromId, toId2);
    toId1 = null;
    toId2 = null;  
      }  
  }
  
  workflowProcess.addContent(transition);
    }
  
private static void addTransition (Element transitions, String id, String from, String to)
  throws JDOMException {
  if ((id == null) || (from == null) || (to == null)) {
      return;
  }
  Element transition = new Element("Transition");
  transition.setAttribute("Id", id);
  transition.setAttribute("From", from);
  transition.setAttribute("To", to);
  transitions.addContent(transition);
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

public static void main(String[] args) throws JDOMException
{
 
 xpdlElement = new Element("Package");
  //definition de la racine du document
 document = new Document(xpdlElement);
 
createXpdlDocument(xpdlElement);
  
showDocument(); //définie plus loin
}

}