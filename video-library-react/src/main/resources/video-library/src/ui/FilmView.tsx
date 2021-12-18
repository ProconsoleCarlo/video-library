import React, {useState} from "react";
import {Film} from "../model/Film";
import {fetchHttpClient} from "../utils/HttpClient";

const httpClient = fetchHttpClient<any, any>();

const dbProtocols = ["jpa", "jdbc"];

interface Props {
}

export const FilmView: React.FC<Props> = () => {
  const [selectedDbProtocol, setSelectedDbProtocol] = useState("jpa");
  const [films, setFilms] = useState<Film[]>([])
  const [jsonBody, setJsonBody] = useState("");

  const retrieveFilms = (dbProtocol: string): Promise<Film[]> => {
    return httpClient.get({url: `/${dbProtocol}/films`})
  }

  const updateFilms = (dbProtocol: string): Promise<Film[]> => {
    return httpClient.put({url: `/${dbProtocol}/films`, body: jsonBody})
  }

  const onRetrieveFilmClick = (): void => {
    retrieveFilms(selectedDbProtocol.toLowerCase())
      .then((films) => {
        setFilms(films);
        setJsonBody(JSON.stringify(films, null, 4));
      })
  }

  const onUpdateFilmClick = (): void => {
    updateFilms(selectedDbProtocol.toLowerCase())
      .then((films) => {
        setFilms(films);
      })
  }

  const onJsonChange = (value: string): void => {
    setJsonBody(value);
  }

  return (
    <div>
      <div>
        <select value={selectedDbProtocol} onChange={(e): void => setSelectedDbProtocol(e.target.value)}>
          {dbProtocols.map((dbProtocol) =>
            <option key={dbProtocol} value={dbProtocol}>{dbProtocol}</option>
          )}
        </select>
        <button onClick={onRetrieveFilmClick}>Retrieve films</button>
      </div>
      <table className="output">
        <tbody>
        <tr>
          <td>ID</td>
          <td>Title</td>
          <td>Year</td>
          <td>Genres</td>
          <td>Reviews</td>
        </tr>
        {
          films.map((film) =>
            <tr key={film.id}>
              <td>{film.id}</td>
              <td>{film.title}</td>
              <td>{film.year}</td>
              <td>{film.genres?.map((genre) => genre.value).join(", ")}</td>
              <td>{film.reviews?.map((review) => review.date + " " + review.rating + " " + review.detail).join(", ")}</td>
            </tr>
          )}
        </tbody>
      </table>
      <div>
        <span>IO text area:</span><br/>
        <textarea rows={12}
                  cols={200}
                  value={jsonBody}
                  onChange={e => onJsonChange(e.target.value)}
        /><br/>
        <button onClick={onUpdateFilmClick}>Update films</button>
      </div>
    </div>
  );
}