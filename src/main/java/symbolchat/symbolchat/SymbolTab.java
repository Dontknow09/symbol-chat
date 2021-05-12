package symbolchat.symbolchat;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import symbolchat.symbolchat.SymbolButton.PasteSymbolButtonWidget;
import symbolchat.symbolchat.SymbolButton.SymbolButtonWidget;

import java.util.ArrayList;
import java.util.List;

public class SymbolTab implements Drawable, Element {

    protected static final int symbolsWidth = 8;
    protected static final int symbolsHeight = 16;

    public static final int width,height;

    public TextFieldWidget textFieldWidget;

    static {
        //size of tabs for number of symbols plus margin
        width = symbolsWidth * SymbolButtonWidget.symbolSize + (SymbolTab.symbolsWidth-1);
        height = symbolsHeight * SymbolButtonWidget.symbolSize + (SymbolTab.symbolsHeight-1);
    }

    protected List<SymbolButtonWidget> symbolButtons;
    protected int x,y;

    protected Screen screen;

    public SymbolTab(Screen screen, SymbolList symbols, int x, int y, TextFieldWidget textFieldWidget) {
        this.x = x;
        this.y = y;
        this.screen = screen;
        this.symbolButtons = new ArrayList<>();
        this.textFieldWidget = textFieldWidget;
        loadSymbols(symbols);
    }

    protected void loadSymbols(SymbolList symbols) {
        for(int i = 0; i < symbols.symbols.size(); i++) {
            int widgetX = this.x+1+(i%symbolsWidth*(SymbolButtonWidget.symbolSize+1));
            int widgetY = this.y+1+(i/symbolsWidth*(SymbolButtonWidget.symbolSize+1));
            SymbolButtonWidget widget = new PasteSymbolButtonWidget(screen, widgetX, widgetY, textFieldWidget, symbols.symbols.get(i));
            symbolButtons.add(widget);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for(SymbolButtonWidget widget : symbolButtons) {
            widget.render(matrices,mouseX,mouseY,delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(SymbolButtonWidget symbolButtonWidget : symbolButtons) {
            if(symbolButtonWidget.mouseClicked(mouseX,mouseY,button)) return true;
        }
        return false;
    }
}