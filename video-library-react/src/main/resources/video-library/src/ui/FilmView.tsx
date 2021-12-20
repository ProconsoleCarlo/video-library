import React from "react";
import {Film} from "../model/Film";
import {GenresView} from "./GenresView";
import {ReviewView} from "./ReviewView";

interface Props {
  film: Film,
  rootPath: string
}

export const FilmView: React.FC<Props> = ({film, rootPath}) => {
  return (
    <>
      <div>Selected film</div>
      <div className={"table"}>
        <div className={"row"}>
          <div className={"col width_40"}>{film.id}</div>
          <div className={"col width_200"}>{film.title}</div>
          <div className={"col width_40"}>{film.year}</div>
        </div>
      </div>
      <GenresView genres={film.genres}/>
      <ReviewView filmId={film.id} reviews={film.reviews} rootPath={rootPath}/>
    </>
  );
}