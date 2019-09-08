import React from "react";
import PropTypes from "prop-types";
import isArray from "lodash/isArray";

const Icon = props => {
  const styles = {
    svg: {
      display: "inline-block",
      verticalAlign: "middle"
    }
  };

  return (
    <svg
      style={styles.svg}
      width={`${props.width || props.size}`}
      height={`${props.height || props.size}`}
      viewBox={props.icon.viewBox || `0 0 50 35`}
      className={props.className}
    >
      {isArray(props.icon.paths) ? (
        props.icon.paths.map((path, key) => <path key={key} d={path} />)
      ) : (
        <path d={props.icon} />
      )}
    </svg>
  );
};

Icon.propTypes = {
  icon: PropTypes.object.isRequired,
  size: PropTypes.number,
  className: PropTypes.string
};

Icon.defaultProps = {
  size: 24,
  className: undefined
};
export default Icon;
