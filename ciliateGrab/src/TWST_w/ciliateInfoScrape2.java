/**
 * 
 */
/**
 * @author gaeaprimeturman
 *
 */

// Program to scrape information on a list of genes from an input file
// off of ciliate.org, and create a tsv (tab seperated file) containing gene information
// by line
// run through eclipse IDE, dependencies loaded in (so only need to be imported)
// if running external to dev env. ensure packages downloaded!!

// update: BUG - selenium driver failure every once in awhile.. noted online but without clear/consistent fix... 
// so cross check last acquired geneInfo on list

package TWST_w;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ciliateInfoScrape2 {
	 
	// method for grabbing the desired information from ciliate.org
	
	static ArrayList<String> geneInfoItems = new ArrayList<String>();
	private static BufferedReader brMissing;
	
	public static void ciliateGrab() {
		
		// first open webdriver
		// navigate to website, input geneName, nav to info page
		
		
				// !!!!*** headless so that no window is opened, toggle on/off to view what code
				// !!!!*** is doing, disable for actual app run
				// toggle 3 lines on/off
				ChromeOptions Ichabod = new ChromeOptions();
		        Ichabod.addArguments("headless");
		        Ichabod.addArguments("window-size=1200x600");
		        Ichabod.addArguments("--dns-prefetch-disable");
				
				
				// Create a new instance of the Chrome driver
					
				String chromeDriverLocation = "/Users/gaeaprimeturman/Desktop/chromedriver";
				System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
				WebDriver driver = new ChromeDriver(Ichabod);
				
				// !!!!!!!!!!!!!!!!!!!!!!!!						!!!!!!!!!!!!!!!!!!
				// TO MAKE HEADLESS RUN CHROMEDRIVER(Ichabod) - else, leave as  !!
				// newChromeDriver(); -- (leave empty)							!!
				// !!!!!!!!!!!!!!!!!!!!!!!!						!!!!!!!!!!!!!!!!!!
		

	    // open file to read from - fix file path to appropriate location
	    String path = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/ciliateGenes.txt";
	    File file = new File(path);
	    // get file to write to - again, fix file path
	    String path2 = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/ciliateStuff.txt";
	    File aFile = new File(path2);
	    try {
	      FileReader fileReader = new FileReader(file);
	      BufferedReader bufferedReader = new BufferedReader(fileReader);

	      // file 1, where ciliate info will go into
	      FileWriter fileWriter = new FileWriter(aFile, true);
	      BufferedWriter Writer = new BufferedWriter(fileWriter);
	      FileReader fileReaderTo = new FileReader(aFile);
	      BufferedReader bufferedReaderTo = new BufferedReader(fileReaderTo);

	      String line;
	      String lineOut;
	      String endGene = "TTHERM_02653301"; // end gene needs to be manually changed to
	      									  // the last gene in your file of genes
	      String lastGene = null;
	      
	      if((lineOut = bufferedReaderTo.readLine()) == null) { //if ciliateStuff is empty
	    	// while still lines in file
    	      while ((line = bufferedReader.readLine()) != null) {
    	    	
    	    	String geneName = line;
    	    	 
    	    	// Navigate to website
    	  		driver.get("http://ciliate.org/index.php/home/welcome");
    	  		
    	  		// Find the quick search bar, click it to place cursor within element
    	  		WebElement qksearch = driver.findElement(By.id("tbxSearchItem"));
    	  		qksearch.click();
    	  		// put in desired gene name
    	  		qksearch.sendKeys(geneName);

    	  		// Find the quick search bar 'submit' button, click to submit search
    	  		WebElement qksubmit = driver.findElement(By.name("btnSearch"));
    	  		qksubmit.click();

    	  		// Find the item for gene Name results
    	  		WebElement geneResults = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/ul/li[1]/a"));
    	  		// click that
    	  		geneResults.click();
    	  		
    	  		// Now nav to the actual gene info page (select first gene option), click button once found (BUT where multiple gene options, select last), activate first option for 
    	  		// when missing some therms. Explaination: In the event the therm is a short ID gene that can be found within other genes (eg. TTHERM101 in TTHERM1012)
    	  		// then the last option will be the desired gene. Therefore check for element presence of more genes on gene select page for the missing genes (in next cilGrab method)
    	  		
    	  		WebElement geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[2]/td[1]/a"));
    	  		
    	  	/*	if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[5]/td[1]/a")).isEmpty()) {
    	  			geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[5]/td[1]/a"));
    	  		} else if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[4]/td[1]/a")).isEmpty()) {
    	  			geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[4]/td[1]/a"));
    	  		} else if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[3]/td[1]/a")).isEmpty()) {
    	  			geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[3]/td[1]/a"));
    	  		} else {
    	  			geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[2]/td[1]/a"));
    	  		}
    	  	*/
    	  			
    	  		// WebElement geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[2]/td[1]/a"));
    	  		geneInfoNav.click();
    	  		
    	  		// Get Gene Model Identifier
    	  		WebElement geneModelId = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[1]"));
    	  		String GeneModelId = geneModelId.getText().split("\n")[1];
    	  		GeneModelId.replaceAll("Gene Model Identifier", "");
    	  		
    	  		String standardName = "None";
    	  		
    	  		// Get Standard Name, could be in two parts, might just be in one (check for this)
    	  		if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]/b/span")).isEmpty()) {
    	  			WebElement stndName = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]"));
    	  			standardName = stndName.getText().split("\n")[1].trim();
    	  			standardName.replaceAll(" ", "|");
    	  		} else {
    	  		WebElement stndName = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]"));
    	  		standardName = stndName.getText().split("\n")[1].trim();
    	  		}
    	  		
    	  		// Get Aliases
    	  		WebElement alss = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[3]"));
    	  		String aliases = alss.getText().split("\n")[1].trim();
    	  		
    	  		// Get Description
    	  		String description = "No Description"; //set description to empty by default
    	  		WebElement descrip = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[2]")); // if exists fill w/ true val
    	  		if (descrip.getText().split("\n").length >= 2) {
    	  		description = descrip.getText().split("\n")[1].trim();
    	  		}
    	  		
    	  		// Now get the stuff that may or may NOT be there
    	  		
    	  		String geneOntinfo = "";
    	  		// Get Gene Ontology Info (if there)
    	  		WebElement ontology = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[2]"));
    	  		if (ontology.getText().startsWith("No")) {
    	  		String geneOntology = ontology.getText().trim();
    	  		geneOntinfo = geneOntology;
    	  		} else {
    	  		String geneOntology = ontology.getText().replaceAll("\n", " ").replaceAll("\\|", ",").replaceAll("Cellular Component", "\\|"+" Cellular Component: ").replaceAll("Biological Process", "\\|"+" Biological Process: ").replaceAll("Molecular Function", "\\|"+" Molecular Function: ").substring(1).trim();
    	  		geneOntinfo = geneOntology;
    	  		}
    	  		// Get Associated Lit
    	  		WebElement assocLit = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[13]"));
    	  		String associatedLit = assocLit.getText().replaceAll("\n", "\\|");
    	  		
    	  		Collections.addAll(geneInfoItems, GeneModelId, standardName, aliases, description, geneOntinfo, associatedLit);
    	    	  
    	    	  // Now get our comma sep string to write to our file
    	    	  String finalInfo = "";
    	            for (int i = 0; i < (geneInfoItems.size()-1); i++) {
    	              finalInfo+=geneInfoItems.get(i) +"\t";
    	              }
    	              finalInfo+=geneInfoItems.get(geneInfoItems.size()-1);
    	              
    	              Writer.write(finalInfo+"\n");
    	              Writer.flush();
    	              geneInfoItems.clear();
    	            }
	      } else { //if not empty
	      while ((lineOut = bufferedReaderTo.readLine()) != null) {
	    	  lastGene = lineOut.split("\t")[0];
		  }
	      if (lastGene.equalsIgnoreCase(endGene)) {
	    	  System.out.println("Webscrape done!");
	      } else { //if last gene in file does NOT equal final gene to grab
	    		  while(!(line = bufferedReader.readLine()).equalsIgnoreCase(lastGene)) {
	    		  }
	    		  line = bufferedReader.readLine();
	    		  // while still lines in file
	    	      while (line != null) {
	    	    	
	    	    	String geneName = line;
	    	    	 
	    	    	// Navigate to website
	    	  		driver.get("http://ciliate.org/index.php/home/welcome");

	    	  		// Find the quick search bar, click it to place cursor within element
	    	  		WebElement qksearch = driver.findElement(By.id("tbxSearchItem"));
	    	  		qksearch.click();
	    	  		// put in desired gene name
	    	  		qksearch.sendKeys(geneName);

	    	  		// Find the quick search bar 'submit' button, click to submit search
	    	  		WebElement qksubmit = driver.findElement(By.name("btnSearch"));
	    	  		qksubmit.click();
	   
	    	  		// Find the item for gene Name results
	    	  		WebElement geneResults = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/ul/li[1]/a"));
	    	  		// click that
	    	  		geneResults.click();
	    	  		
	    	  		// Now nav to the actual gene info page (select first gene option), click button once found (BUT where multiple gene options, select last)
	    	  		WebElement geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[2]/td[1]/a"));
	    	  			
	    	  		geneInfoNav.click();

	    	  		// Get Gene Model Identifier
	    	  		WebElement geneModelId = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[1]"));
	    	  		String GeneModelId = geneModelId.getText().split("\n")[1];
	    	  		GeneModelId.replaceAll("Gene Model Identifier", "");
	    	  		
	    	  		String standardName = "None";
	    	  		
	    	  		// Get Standard Name, could be in two parts, might just be in one (check for this)
	    	  		if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]/b/span")).isEmpty()) {
	    	  			WebElement stndName = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]"));
	    	  			standardName = stndName.getText().split("\n")[1].trim();
	    	  			standardName.replaceAll(" ", "|");
	    	  		} else {
	    	  		WebElement stndName = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]"));
	    	  		standardName = stndName.getText().split("\n")[1].trim();
	    	  		}
	    	  		
	    	  		// Get Aliases
	    	  		WebElement alss = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[3]"));
	    	  		String aliases = alss.getText().split("\n")[1].trim();
	    	  		
	    	  		// Get Description
	    	  		String description = "No Description"; //set description to empty by default
	    	  		WebElement descrip = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[2]")); // if exists fill w/ true val
	    	  		if (descrip.getText().split("\n").length >= 2) {
	    	  		description = descrip.getText().split("\n")[1].trim();
	    	  		}
	    	  		
	    	  		// Now get the stuff that may or may NOT be there
	    	  		
	    	  		String geneOntinfo = "";
	    	  		// Get Gene Ontology Info (if there)
	    	  		WebElement ontology = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[2]"));
	    	  		if (ontology.getText().startsWith("No")) {
	    	  		String geneOntology = ontology.getText().trim();
	    	  		geneOntinfo = geneOntology;
	    	  		} else {
	    	  		String geneOntology = ontology.getText().replaceAll("\n", " ").replaceAll("\\|", ",").replaceAll("Cellular Component", "\\|"+" Cellular Component: ").replaceAll("Biological Process", "\\|"+" Biological Process: ").replaceAll("Molecular Function", "\\|"+" Molecular Function: ").substring(1).trim();
	    	  		geneOntinfo = geneOntology;
	    	  		}
	    	  		// Get Associated Lit
	    	  		WebElement assocLit = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[13]"));
	    	  		String associatedLit = assocLit.getText().replaceAll("\n", "\\|");
	    	  		
	    	  		Collections.addAll(geneInfoItems, GeneModelId, standardName, aliases, description, geneOntinfo, associatedLit);
	    	    	  
	    	    	  // Now get our comma sep string to write to our file
	    	    	  String finalInfo = "";
	    	            for (int i = 0; i < (geneInfoItems.size()-1); i++) {
	    	              finalInfo+=geneInfoItems.get(i) +"\t";
	    	              }
	    	              finalInfo+=geneInfoItems.get(geneInfoItems.size()-1);
	    	              
	    	              fileWriter.write(finalInfo+"\n");
	    	              fileWriter.flush();
	    	              geneInfoItems.clear();
	    	              line = bufferedReader.readLine();
	    	            }
	    	  
	      }
	      }
	      
	      fileReader.close();
	      bufferedReader.close();
	      fileReaderTo.close(); 
	      bufferedReaderTo.close();

	      //flush and close
	      Writer.close();
	      fileWriter.close();

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		

				//Close the browser
				driver.close();
				driver.quit();
				return;
	}
	
	// assumes file will NOT be empty, assumes function completes before web page timeout (required < 5 min. run time for ~100 missing genes of 27000 genes)
	public static void ciliateGrabShort(String endGene) { // grab the short length THERMS for which the first selected gene
														  // is a different gene
		
		// first open webdriver
		// navigate to website, input geneName, nav to info page
		
		
				// !!!!*** headless so that no window is opened, toggle on/off to view what code
				// !!!!*** is doing, disable for actual app run
				// toggle 3 lines on/off
				ChromeOptions Ichabod = new ChromeOptions();
		        Ichabod.addArguments("headless");
		        Ichabod.addArguments("window-size=1200x600");
		        Ichabod.addArguments("--dns-prefetch-disable");
				
				
				// Create a new instance of the Chrome driver
					
				String chromeDriverLocation = "/Users/gaeaprimeturman/Desktop/chromedriver";
				System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
				WebDriver driver = new ChromeDriver(Ichabod);
				
				// !!!!!!!!!!!!!!!!!!!!!!!!						!!!!!!!!!!!!!!!!!!
				// TO MAKE HEADLESS RUN CHROMEDRIVER(Ichabod) - else, leave as  !!
				// newChromeDriver(); -- (leave empty)							!!
				// !!!!!!!!!!!!!!!!!!!!!!!!						!!!!!!!!!!!!!!!!!!
		
		
	    // open file to read from
		String path = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/missingGenes.txt";
		File file = new File(path);
		// get file to write to
		String path2 = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/ciliateStuffClean.txt";
		File aFile = new File(path2);
	    try {

			
	      FileReader fileReader = new FileReader(file);
	      BufferedReader bufferedReader = new BufferedReader(fileReader);

	      // file 1, where ciliate info will go into
	      FileWriter fileWriter = new FileWriter(aFile, true);
	      BufferedWriter Writer = new BufferedWriter(fileWriter);
	      FileReader fileReaderTo = new FileReader(aFile);
	      BufferedReader bufferedReaderTo = new BufferedReader(fileReaderTo);

	      String line;
	      String lineOut;
	      
	      
	      String lastGene = null;
	      
	      while ((lineOut = bufferedReaderTo.readLine()) != null) {
	    	  lastGene = lineOut.split("\t")[0];
		  }
	      
	      if (endGene.equalsIgnoreCase(lastGene)) {
	    	  System.out.println("Webscrape done!");
	      }
	      if (lastGene.equalsIgnoreCase(endGene)) {
	    	  System.out.println("Webscrape done!");
	      } else { //if last gene in file does NOT equal final gene to grab
	    		
	    		  // while still lines in file
	    	      while ((line = bufferedReader.readLine()) != null) {
	    	    	
	    	    	String geneName = line;
	    	    	 
	    	    	// Navigate to website
	    	  		driver.get("http://ciliate.org/index.php/home/welcome");

	    	  		// Find the quick search bar, click it to place cursor within element
	    	  		WebElement qksearch = driver.findElement(By.id("tbxSearchItem"));
	    	  		qksearch.click();
	    	  		// put in desired gene name
	    	  		qksearch.sendKeys(geneName);

	    	  		// Find the quick search bar 'submit' button, click to submit search
	    	  		WebElement qksubmit = driver.findElement(By.name("btnSearch"));
	    	  		qksubmit.click();
	   
	    	  		// Find the item for gene Name results
	    	  		WebElement geneResults = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/ul/li[1]/a"));
	    	  		// click that
	    	  		geneResults.click();
	    	  		
	    	  		
	    	  		WebElement geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[2]/td[1]/a"));
	    	  		if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[5]/td[1]/a")).isEmpty()) {
	    	  			geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[5]/td[1]/a"));
	    	  		} else if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[4]/td[1]/a")).isEmpty()) {
	    	  			geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[4]/td[1]/a"));
	    	  		} else if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[3]/td[1]/a")).isEmpty()) {
	    	  			geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[3]/td[1]/a"));
	    	  		} else {
	    	  			geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[2]/td[1]/a"));
	    	  		}
	    	  			
	    	  		// WebElement geneInfoNav = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/table/tbody/tr[2]/td[1]/a"));
	    	  		geneInfoNav.click();

	    	  		// Get Gene Model Identifier
	    	  		WebElement geneModelId = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[1]"));
	    	  		String GeneModelId = geneModelId.getText().split("\n")[1];
	    	  		GeneModelId.replaceAll("Gene Model Identifier", "");
	    	  		
	    	  		String standardName = "None";
	    	  		
	    	  		// Get Standard Name, could be in two parts, might just be in one (check for this)
	    	  		if (!driver.findElements(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]/b/span")).isEmpty()) {
	    	  			WebElement stndName = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]"));
	    	  			standardName = stndName.getText().split("\n")[1].trim();
	    	  			standardName.replaceAll(" ", "|");
	    	  		} else {
	    	  		WebElement stndName = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[2]"));
	    	  		standardName = stndName.getText().split("\n")[1].trim();
	    	  		}
	    	  		
	    	  		// Get Aliases
	    	  		WebElement alss = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[1]/div[3]"));
	    	  		String aliases = alss.getText().split("\n")[1].trim();
	    	  		
	    	  		// Get Description
	    	  		String description = "No Description"; //set description to empty by default
	    	  		WebElement descrip = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[1]/div[2]")); // if exists fill w/ true val
	    	  		if (descrip.getText().split("\n").length >= 2) {
	    	  		description = descrip.getText().split("\n")[1].trim();
	    	  		}
	    	  		
	    	  		// Now get the stuff that may or may NOT be there
	    	  		
	    	  		String geneOntinfo = "";
	    	  		// Get Gene Ontology Info (if there)
	    	  		WebElement ontology = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[2]"));
	    	  		if (ontology.getText().startsWith("No")) {
	    	  		String geneOntology = ontology.getText().trim();
	    	  		geneOntinfo = geneOntology;
	    	  		} else {
	    	  		String geneOntology = ontology.getText().replaceAll("\n", " ").replaceAll("\\|", ",").replaceAll("Cellular Component", "\\|"+" Cellular Component: ").replaceAll("Biological Process", "\\|"+" Biological Process: ").replaceAll("Molecular Function", "\\|"+" Molecular Function: ").substring(1).trim();
	    	  		geneOntinfo = geneOntology;
	    	  		}
	    	  		// Get Associated Lit
	    	  		WebElement assocLit = driver.findElement(By.xpath("//*[@id=\"contentExpand\"]/div/div/blockquote[13]"));
	    	  		String associatedLit = assocLit.getText().replaceAll("\n", "\\|");
	    	  		
	    	  		Collections.addAll(geneInfoItems, GeneModelId, standardName, aliases, description, geneOntinfo, associatedLit);
	    	    	  
	    	    	  // Now get our comma sep string to write to our file
	    	    	  String finalInfo = "";
	    	            for (int i = 0; i < (geneInfoItems.size()-1); i++) {
	    	              finalInfo+=geneInfoItems.get(i) +"\t";
	    	              }
	    	              finalInfo+=geneInfoItems.get(geneInfoItems.size()-1);
	    	              
	    	              fileWriter.write(finalInfo+"\n");
	    	              fileWriter.flush();
	    	              geneInfoItems.clear();
	    	              //line = bufferedReader.readLine();
	    	            }
	    	  
	      }
	      
	      
	      fileReader.close();
	      bufferedReader.close();
	      fileReaderTo.close(); 
	      bufferedReaderTo.close();
	      brMissing.close();

	      //flush and close
	      Writer.close();
	      fileWriter.close();

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		

				//Close the browser
				driver.close();
				driver.quit();
				return;
	}
	
	public static void DeleteDupes() throws IOException
    {
        // PrintWriter object for output.txt
		String outFilepath = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/ciliateStuffClean.txt";
        PrintWriter pw = new PrintWriter(outFilepath);

        // BufferedReader object for input.txt
        String inFilepath = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/ciliateStuff.txt";
        BufferedReader br = new BufferedReader(new FileReader(inFilepath));

        String line = br.readLine();

        // set store unique values
        HashSet<String> hs = new HashSet<String>();

        // loop for each line of input.txt
        while(line != null)
        {
            // write only if not
            // present in hashset
            if(hs.add(String.valueOf(line.split("\t")[0].trim())))
                pw.println(line);

            line = br.readLine();

        }

        pw.flush();

        // closing resources
        br.close();
        pw.close();
        
        System.out.println("File operation performed successfully");

    }
	
	public static void confirmGenes() throws IOException
	  {

	    // PrintWriter object for output.txt
		String outFilepath = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/missingGenes.txt";
        PrintWriter pw = new PrintWriter(outFilepath);

	    // BufferedReader object for input.txt
        String inFilepath = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/ciliateStuffClean.txt";
        BufferedReader br = new BufferedReader(new FileReader(inFilepath));

	    // BufferedReader object for input.txt 2
        String inFilepath2 = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/ciliateGenes.txt";
        BufferedReader br2 = new BufferedReader(new FileReader(inFilepath2));

	    String line = br.readLine(); // line == cil stuff
	    String line2 = br2.readLine();

	    // set store unique values
	    HashMap<Integer,String> hm = new HashMap<Integer,String>();

	    String[] toppings = new String[27000];

	    int count=0;
	    while(line != null)
	    {
	      // write only if not
	      // present in hashset
	      hm.put(count,line.split("\t")[0].trim());

	      line = br.readLine();
	      count+=1;
	    }

	    int count2 = 0;
	    // loop for each line of input.txt
	    while(line2 != null)
	    {
	      // write only if not
	      // present in hashset
	      if(hm.containsValue(line2.trim())) {
	        count2+=1;
	      } else {
	        toppings[count2] = line2;
	        count2+=1;
	      }

	      line2 = br2.readLine();

	    }

	    ArrayList<String> list = new ArrayList<String>();
	    for (String s : toppings) {
	      if (s != (null)) {
	        list.add(s);
	      }
	    }

	    for (int i = 0; i < list.size(); i++) {
	      pw.println(list.get(i));
	    }

	    pw.flush();

	    // closing resources
	    br.close();
	    br2.close();
	    pw.close();

	    System.out.println("File operation performed successfully");
	  }
	
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Scraping info from ciliate.org...");
		ciliateGrab();
		System.out.println("Finished web scrape, checking for duplicates...");
		try {
			DeleteDupes();
			System.out.println("Duplicates deleted, checking for any missing genes...");
			confirmGenes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// BufferedReader object for missing genes left to add
		// first get the last gene in this file
		String inFilepath = "/Users/gaeaprimeturman/eclipse-workspace/ciliateGrab/src/TWST_w/missingGenes.txt";
				
		brMissing = new BufferedReader(new FileReader(inFilepath));
		String BRline = brMissing.readLine(); 
		String endGene = "";      
		while(BRline != null) {
			endGene = BRline.split("\t")[0].trim();
			BRline = brMissing.readLine();
		}
		System.out.println("Filling in missing genes...");
		if (endGene.equals("")) {
			System.out.println("All done!");
		} else {
			ciliateGrabShort(endGene);
			System.out.println("All done!");
		}
		
	}

}