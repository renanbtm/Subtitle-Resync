package model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SrtFile {
	
	private String path;
	private String name;
	private List<String> lines = new ArrayList<String>();
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getLines() {
		return lines;
	}
	public void setLines(int offset) {
		try {
			this.lines = Files.readAllLines(Paths.get(this.path), Charset.defaultCharset());
			String regex = "\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d --> \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d";
			Pattern p = Pattern.compile(regex);
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				if (p.matcher(line).matches()) {
					Time time = new Time(line);
					time.adjustTime(offset);
					line = time.getFormattedTime();
					lines.set(i, line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
