#version 330

in vec4 interpolatedColour;
out vec4 outputColor;

void main() {
    outputColor = interpolatedColour;
}