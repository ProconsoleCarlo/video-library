import React, {useState} from "react";
import {Review} from "../model/Film";
import {NewReview} from "../model/NewFilm";
import {fetchHttpClient} from "../utils/HttpClient";

interface Props {
  filmId: number,
  reviews: Review[],
  rootPath: string
}

const EMPTY_REVIEW = (filmId: number): NewReview => {
  return {
    id: null,
    date: "",
    rating: 6,
    detail: null,
    filmId: filmId
  }
}

const httpClient = fetchHttpClient<any, any>();

export const ReviewView: React.FC<Props> = ({filmId, reviews, rootPath}) => {
  const [jsonReview, setJsonReview] = useState(JSON.stringify(EMPTY_REVIEW(filmId), null, 4));
  const addReview = (dbProtocol: string): Promise<Review> => {
    return httpClient.put({url: `/${dbProtocol}/review`, body: jsonReview})
  }
  const updateReview = (dbProtocol: string): Promise<Review> => {
    return httpClient.post({url: `/${dbProtocol}/review`, body: jsonReview})
  }

  const saveReview = (): void => {
    const review: NewReview = JSON.parse(jsonReview)
    if (review.id != null) {
      updateReview(rootPath)
        .then((review) => {
          console.log("review updated");
          console.log(review);
        })
    } else {
      addReview(rootPath)
        .then((review) => {
          console.log("review inserted");
          console.log(review);
          reviews.push(review);
        })
    }
  }

  return (
    <>
      <div className={"row"}>Reviews</div>
      <div className={"table"}>
        {
          reviews.map((review) =>
            <div className={"row"} onClick={() => setJsonReview(JSON.stringify(review, null, 4))}>
              <div className={"col width_40"}>{review.id}</div>
              <div className={"col width_200"}>{review.date}</div>
              <div className={"col width_40"}>{review.rating}</div>
              <div className={"col width_200"}>{review.detail}</div>
            </div>
          )
        }
      </div>
      <div>
        <span>New review</span><br/>
        <textarea rows={12}
                  cols={200}
                  value={jsonReview}
                  onChange={e => setJsonReview(e.target.value)}
        /><br/>
        <button onClick={saveReview}>Update films</button>
      </div>
    </>
  );
}