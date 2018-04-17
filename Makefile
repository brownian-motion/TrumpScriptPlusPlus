SOURCE_FILES = $(shell find src/ \( -name "*.java" -o -name "*.trump" \) )

all: display

trumpscript-parser-jjbrown-113223831.md: Makefile MakePrintout.sh $(SOURCE_FILES)
	./MakePrintout.sh > $@

# see https://tex.stackexchange.com/questions/179926/pandoc-markdown-to-pdf-without-cutting-off-code-block-lines-that-are-too-long
%.pdf: %.md listings-setup.tex
	-pandoc -f markdown -t latex -o "$@" -H listings-setup.tex --listings --pdf-engine=xelatex "$<"

display: trumpscript-parser-jjbrown-113223831.pdf
	explorer $<;

clean:
	rm -rf trumpscript-parser-jjbrown-113223831.pdf trumpscript-parser-jjbrown-113223831.md tex2pdf*
