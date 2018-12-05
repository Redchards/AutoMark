package com.auto.mark;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
/**
 * A class used to parse the notation configuration file.
 */
public class MarkingConfigurationParser {
	
	private File notationConfigurationFile;
	private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withIgnoreSurroundingSpaces().withDelimiter(';');
	private static final int LOWER_SUBSTRACTED_VALUE_INDEX = 1;
	private static final int UPPER_SUBSTRACTED_VALUE_INDEX = 2;
	private static final int LOWER_REPETITION_VALUE_INDEX = 3;
	private static final int UPPER_REPETITION_VALUE_INDEX = 4;
	private static final int MODIFIER_VALUE_INDEX = 5;
	private static final int DEFAULT_RULE_SIZE = 5;
	private static final int DEFAULT_METRICS_SIZE = 6;
	
	/**
	 * Build from a notation configuration file.
	 * 
	 * @param notationConfigurationFile The notation configuration file to be used.
	 */
	public MarkingConfigurationParser(File notationConfigurationFile) {
		this.notationConfigurationFile = notationConfigurationFile;
	}
	
	/**
	 * Build from a notation configuration file path.
	 * 
	 * @param notationConfigurationFilePath The notation configuration file path to be used.
	 */
	public MarkingConfigurationParser(String notationConfigurationFilePath) {
		this(new File(notationConfigurationFilePath));
	}

	/**
	 * @return the internal notation configuration file.
	 */
	public File getNotationConfigurationFile() {
		return notationConfigurationFile;
	}
	
	/**
	 * Parse the configuration file and return the {@MarkingConfiguration}.
	 * 
	 * @return the @{MarkingConfiguration} generated.
	 * 
	 * @throws BadNotationConfigurationException
	 */
	public MarkingConfiguration parse() throws BadNotationConfigurationException {
		
		MarkingConfiguration markingConfiguration = new MarkingConfiguration();
		
		try(Reader in = new FileReader(notationConfigurationFile);
			CSVParser parser = new CSVParser(in,FORMAT)) {
			Iterator<CSVRecord> it = parser.iterator();
			int lignCounter = 0;
			
			CSVRecord firstRecord = it.next();
			lignCounter++;
			if(firstRecord.size() != 1){
				throw new BadNotationConfigurationException(lignCounter + ". It must be a double which will be the minimal code quality factor.");
			}
			markingConfiguration.setMinFactor(Double.parseDouble(firstRecord.get(0))); // getting the lower code quality factor which will modulate the grade of unit testing.
			while(it.hasNext()) {
				CSVRecord record = it.next();
				lignCounter++;
				String ruleCode = record.get(0);
				DoubleRange doubleRange = null;
				IntegerRange integerRange = null;
				SonarRule sonarRule = null;
				doubleRange = getSubstractionRange(record, lignCounter);
				integerRange = getRepetitionRange(record, lignCounter);
				switch(record.size()) { // here we are checking the size of the csv's line to make sure it's correct. And if it's good, the rule is added to the hashmap.
					case DEFAULT_RULE_SIZE :
						sonarRule = new SonarRule(ruleCode, doubleRange, integerRange);
						markingConfiguration.addRule(ruleCode, sonarRule);
						break;
					case DEFAULT_METRICS_SIZE :
						String modifier = record.get(MODIFIER_VALUE_INDEX);
						
						sonarRule = new SonarRule(ruleCode,doubleRange,integerRange, modifier);
						markingConfiguration.addRule(ruleCode, sonarRule);
						break;
					default:
						throw new BadNotationConfigurationException("The size of the rule or the metrics isn't respected.", lignCounter); 
						
				}
			}
		} catch (IOException e) {
			throw new BadNotationConfigurationException("When parsing the configuration file", e);
		}
		
		return markingConfiguration;
	}
	
	private DoubleRange getSubstractionRange(CSVRecord record, int lineNo) throws BadNotationConfigurationException {
		DoubleRange range = null;
		
		try {
			range = new DoubleRange(Double.parseDouble(record.get(LOWER_SUBSTRACTED_VALUE_INDEX)),
									Double.parseDouble(record.get(UPPER_SUBSTRACTED_VALUE_INDEX)));
		} catch(InvalidRangeException e){
			throw new BadNotationConfigurationException("Range error", lineNo, e);
		} catch(NumberFormatException e) {
			throw new BadNotationConfigurationException("Detected bad numeric value", lineNo, e);
		}
		
		return range;
	}
	
	private IntegerRange getRepetitionRange(CSVRecord record, int lineNo) throws BadNotationConfigurationException {
		IntegerRange range = null;
		
		try {
			range = new IntegerRange(Integer.parseInt(record.get(LOWER_REPETITION_VALUE_INDEX)),
					 Integer.parseInt(record.get(UPPER_REPETITION_VALUE_INDEX)));
		} catch(InvalidRangeException e){
			throw new BadNotationConfigurationException("Range error", lineNo, e);
		} catch(NumberFormatException e) {
			throw new BadNotationConfigurationException("Detected bad numeric value", lineNo, e);
		}
		
		return range;
	}

}
