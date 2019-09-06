import React from "react";
import Table from "react-bootstrap/Table";
import "./CommitList.css";
import Avatar from "../avatar/Avatar";

const CommitList = ({ commits, isLoading, totalCommits }) => {
  return (
    <Table className="mt-3" responsive>
      <thead>
        <tr>
          <th colSpan="2">
            <span className="header text-secondary">Author</span>
          </th>
          <th>
            <span className="header text-secondary">Commit</span>
          </th>
          <th>
            <span className="header text-secondary">Message</span>
          </th>
          <th>
            <span className="header text-secondary">Created</span>
          </th>
        </tr>
      </thead>
      <tbody>
        {commits.map((commit, key) => (
          <tr key={key}>
            <td className="avatar-column">
              <Avatar
                url={commit.committer.avatarUrl}
                name={commit.committer.name}
              />
            </td>
            <th className="ellipsis ">
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
          <td colSpan="5">{`Showing ${commits.length} of ${totalCommits}`}</td>
        </tr>
        {isLoading && (
          <tr>
            <td colSpan="5">Loading...</td>
          </tr>
        )}
      </tfoot>
    </Table>
  );
};

export default CommitList;
