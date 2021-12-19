import React from "react";
import {Film} from "../model/Film";

interface Props {
  films: Film[],
  onFilmClick: (film: Film) => void
}

export const FilmListView: React.FC<Props> = ({films, onFilmClick}) => {
  return (
    <>
      <div>List of films</div>
      <div className={"table"}>
        <div className={"row"}>
          <div className={"col width_40"}>ID</div>
          <div className={"col width_200"}>Title</div>
          <div className={"col width_40"}>Year</div>
        </div>
        {
          films.map((film) =>
            <div className={"row"} onClick={() => onFilmClick(film)}>
              <div className={"col width_40"}>{film.id}</div>
              <div className={"col width_200"}>{film.title}</div>
              <div className={"col width_40"}>{film.year}</div>
            </div>
          )
        }
      </div>
    </>
  );
}