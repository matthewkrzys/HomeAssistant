import ShoppingListView from "../views/ShoppingListView";
import { ViewType } from "../App";

interface ContentProps {
  view: ViewType;
}

const Content = ({ view }: ContentProps) => {
  switch (view) {
    case "shopping":
      return <ShoppingListView />;
    default:
      return null;
  }
};

export default Content;
