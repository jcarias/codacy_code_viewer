export const computeColorsForCode = charCode => {
  let colors = {
    foreGround: "black",
    backGround: "white"
  };

  if (charCode) {
    let codeOffset = charCode >= 65 && charCode <= 90 ? 64 : 96;
    let hueValue = (360 / 26) * (charCode - codeOffset);
    console.log(charCode, hueValue, colors);
    colors.backGround = `hsl(${hueValue},100%,50%)`;
    colors.foreGround = charCode - codeOffset <= 13 ? "black" : "white";
  }

  return colors;
};
