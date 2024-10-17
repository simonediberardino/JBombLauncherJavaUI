package presentation.components

import values.BomberColors
import java.awt.Color

class BlackButton(text: String, font: Int) : BombermanButton(text, font) {
    override fun getBorderColor(): Color {
        return BomberColors.BLACK
    }

    override fun getMouseHoverBackgroundColor(): Color {
        return Color.GRAY
    }
}