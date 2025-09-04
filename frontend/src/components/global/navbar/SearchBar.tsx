import searchIconImage  from "../../../assets/global/navbar/search-icon.png"
import type { SearchBarProps } from "../../../props/global/navbar/SearchBarProps"

const SearchBar = (props: SearchBarProps) => {
  return (
    <div className="w-full h-full flex justify-center items-center !pb-2">
      <div className="bg-sunny-yellow h-1/2 w-19/20 rounded-3xl flex items-center">
        <div className="flex justify-start w-8/10">
          <button className="h-3/5 !px-3">
            <img src={ searchIconImage } alt="Search icon" className=" 
               hover:brightness-250 transisition duration-150 ease-in"/>
          </button>
          <input type="text" placeholder="Seach for university" 
            className="placeholder:text-dark-blue placeholder:text-[1.3vw]
              placeholder:!pl-1 placeholder:font-normal w-9/10
              focus:outline-none focus:placeholder-none
              text-dark-blue font-semibold text-[1.2vw]"/>
        </div>
        <div className="w-2/10 flex justify-end !pr-1.5">
          <button className="bg-dark-blue rounded-3xl text-sunny-yellow !py-1.5 !px-6
            font-semibold text-[1vw] hover:brightness-115 transition duration-125 ease-in"
            onClick={ props.onLanguageChange }>
              { props.searchLanguage }
          </button>
        </div>
      </div>
    </div>
  )
}

export default SearchBar