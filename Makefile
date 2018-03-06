
all: display

trumpscript-tokenizer-jjbrown-113223831.md: Makefile MakePrintout.sh $(shell find src/ \( -name "*.java" -o -name "*.trump*" \) )
	./MakePrintout.sh > $@

%.pdf: %.md listings-setup.tex
	-pandoc -f markdown -t latex -o "$@" -H listings-setup.tex --listings --pdf-engine=xelatex "$<"

display: trumpscript-tokenizer-jjbrown-113223831.pdf
	explorer $<;

clean:
	rm -rf trumpscript-tokenizer-jjbrown-113223831.pdf trumpscript-tokenizer-jjbrown-113223831.md