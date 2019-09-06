import React from "react";
import Table from "react-bootstrap/Table";
import "./CommitList.css";

const CommitList = ({ commits, isLoading, totalCommits }) => {
  return (
    <Table responsive>
      <thead>
        <tr>
          <th>Author</th>
          <th>Committed</th>
          <th>Message</th>
          <th>Created</th>
        </tr>
      </thead>
      <tbody>
        {commits.map((commit, key) => (
          <tr key={key}>
            <th className="ellipsis">
              <span>{commit.committer.name}</span>
            </th>
            <td className="ellipsis">
              <span>{commit.sha}</span>
            </td>
            <td className="ellipsis">
              <span>{commit.message}</span>
            </td>
            <td className="ellipsis">
              <span>{commit.date}</span>
            </td>
          </tr>
        ))}
      </tbody>
      <tfoot>
        <tr>
          <td colSpan="4">{`Showing ${commits.length} of ${totalCommits}`}</td>
        </tr>
        {isLoading && (
          <tr>
            <td colSpan="4">Loading...</td>
          </tr>
        )}
      </tfoot>
    </Table>
  );
};

export default CommitList;
