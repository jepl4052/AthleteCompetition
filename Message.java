/*
 * Jens Plate. jepl4052
 * Erik Hörnström, erho7892
 */
public class Message {

	public Message () {

	}

	public void printMessage(String s) {
		System.out.println();
		writeFullLine();
		writePaddingLine();
		writeMessageLine(s);
		writePaddingLine();
		writeFullLine();
		System.out.println();
	}

	private void writeFullLine() {
		for (int i = 0; i < 59; i++) {
			System.out.print("#");
		}
		System.out.println("#");
	}

	private void writePaddingLine() {
		System.out.print("#");
		for (int i = 0; i < 58; i++) {
			System.out.print(" ");
		}
		System.out.println("#");
	}

	private void writeMessageLine(String s) {
		System.out.print("#");
		System.out.printf(" %-56.56S ", s.substring(8));
		System.out.println("#");
	}

}
