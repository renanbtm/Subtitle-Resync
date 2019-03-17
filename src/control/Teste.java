package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import model.Time;

public class Teste {
	
	public static void main(String[] args) throws IOException {
		int offset = -74000;
		List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\renan\\Videos\\Game.of.Thrones.Season.1.S01.1080p.10bit.BluRay.5.1.x265.HEVC-MZABI\\S01E07 - You Win or You Die.srt"), Charset.defaultCharset());
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
		File newFile = new File("C:\\Users\\renan\\Videos\\Game.of.Thrones.Season.1.S01.1080p.10bit.BluRay.5.1.x265.HEVC-MZABI\\S01E07 - You Win or You Die.srt");
		FileWriter fw = new FileWriter(newFile, false);
		for (int i = 0; i < lines.size(); i++) fw.write(lines.get(i) + System.lineSeparator());
		fw.close();
	}

}
