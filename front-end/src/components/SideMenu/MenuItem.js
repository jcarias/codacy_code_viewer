import React from "react";
import Icon from "../Icon";

import "./MenuItem.css";

const MenuItem = ({ label, icon, selected, handleSelection }) => {
  return (
    <div
      className={`menu-item-root ${selected ? "selected" : ""}`}
      onClick={handleSelection}
    >
      <Icon className="menu-item-icon" icon={icon} size={36} height={25}></Icon>
      <span className="menu-item-label">{label}</span>
    </div>
  );
};

export default MenuItem;
